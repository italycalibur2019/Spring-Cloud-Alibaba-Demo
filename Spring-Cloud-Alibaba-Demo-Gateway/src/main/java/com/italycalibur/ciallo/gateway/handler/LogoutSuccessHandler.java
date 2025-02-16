package com.italycalibur.ciallo.gateway.handler;

import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.domain.Result;
import com.italycalibur.ciallo.common.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-14 00:59:56
 * @description: 自定义登出成功处理器
 */
@Component
public class LogoutSuccessHandler implements ServerLogoutSuccessHandler {
    @Resource
    private JwtTokenProperty jwtTokenProperty;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        ServerWebExchange webExchange = exchange.getExchange();
        ServerHttpRequest request = webExchange.getRequest();
        ServerHttpResponse response = webExchange.getResponse();
        // 获取token信息
        String token = request.getHeaders().getFirst(jwtTokenProperty.getHeader());
        if (StringUtils.hasLength(token)) {
            // 验证redis中是否存在token
            String realToken = token.substring(jwtTokenProperty.getPrefix().length());
            if (StringUtils.hasLength(realToken) && redisTemplate.hasKey(realToken)) {
                redisTemplate.delete(realToken);
            }
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().setCacheControl("no-store,no-cache,must-revalidate,max-age-8");
        Result<Map<String, Object>> result = Result.ok();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(result.asJsonString().getBytes())));
    }
}
