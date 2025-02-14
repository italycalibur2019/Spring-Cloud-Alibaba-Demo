package com.italycalibur.ciallo.security.handler;

import com.italycalibur.ciallo.common.domain.Result;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
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
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        ServerWebExchange webExchange = exchange.getExchange();
        ServerHttpResponse response = webExchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().setCacheControl("no-store,no-cache,must-revalidate,max-age-8");
        Result<Map<String, Object>> result = Result.ok();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(result.asJsonString().getBytes())));
    }
}
