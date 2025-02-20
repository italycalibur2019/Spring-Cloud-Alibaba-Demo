package com.italycalibur.ciallo.gateway.filter;

import com.italycalibur.ciallo.common.enums.GatewayServiceEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 19:43:20
 * @description: 全局过滤器
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获得路由
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        // 获得路由id
        String routeId = ObjectUtils.isEmpty(route) ? "unknown" : route.getId();
        // 打印日志
        log.info("====== (●'◡'●) 过滤器正常工作~ 当前访问服务：{}，访问路径：{} ======", GatewayServiceEnums.getDescByCode(routeId), exchange.getRequest().getURI().getPath());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() { return Integer.MIN_VALUE; }
}
