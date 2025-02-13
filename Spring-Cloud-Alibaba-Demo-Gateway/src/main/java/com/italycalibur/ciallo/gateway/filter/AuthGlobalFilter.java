package com.italycalibur.ciallo.gateway.filter;

import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.configuration.properties.SecureUrlProperty;
import com.italycalibur.ciallo.common.domain.Result;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 19:43:20
 * @description: gateway 全局鉴权过滤器
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private JwtTokenProperty jwtTokenProperty;
    @Resource
    private SecureUrlProperty secureUrlProperty;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("======过滤器正常工作======");
        // 请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 请求地址
        String url = request.getURI().getPath();
        if (this.shouldNotFilter(url)) {
            return chain.filter(exchange);
        }
        // 获取token信息
        String token = request.getHeaders().getFirst(jwtTokenProperty.getHeader());
        if (!StringUtils.hasLength(token)) {
            return getUnAuthorizedMono(response);
        }
        // 验证redis中是否存在token
        String realToken = token.substring(jwtTokenProperty.getPrefix().length());
        if (!StringUtils.hasLength(realToken) || !redisTemplate.hasKey(realToken)) {
            return getUnAuthorizedMono(response);
        }
        // 把新的exchange 放回到过滤链
        ServerHttpRequest newRequest = request.mutate().header(jwtTokenProperty.getHeader(), token).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() { return Integer.MIN_VALUE; }

    /**
     * 是否需要过滤
     *
     * @param url 请求地址
     * @return true：不需要过滤
     */
    private boolean shouldNotFilter(String url) {
        for (String s : secureUrlProperty.getUrls()) {
            if (url.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 未登录或登录超时
     *
     * @param response 响应对象
     * @return 响应对象
     */
    private Mono<Void> getUnAuthorizedMono(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorMsg = Result.unauthorized().asJsonString();

        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMsg.getBytes())));
    }
}
