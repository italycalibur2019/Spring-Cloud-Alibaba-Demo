package com.italycalibur.ciallo.security.handler;

import com.italycalibur.ciallo.common.domain.Result;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-14 00:18:23
 * @description: 自定义登录失败处理器
 */
@Component
public class LoginFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().setCacheControl("no-store,no-cache,must-revalidate,max-age-8");
        Result<Map<String, Object>> result = Result.unauthorized();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(result.asJsonString().getBytes())));
    }
}
