package com.italycalibur.ciallo.gateway.handler;

import com.alibaba.fastjson2.JSON;
import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.utils.JwtUtils;
import com.italycalibur.ciallo.gateway.user.AuthUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-14 01:10:55
 * @description: 自定义 JWT 认证过滤器
 */
@Component
public class JwtTokenAuthenticationFilter implements WebFilter {
    private final JwtUtils jwtUtils;
    private final JwtTokenProperty jwtTokenProperty;

    public JwtTokenAuthenticationFilter(JwtTokenProperty jwtTokenProperty, JwtUtils jwtUtils) {
        this.jwtTokenProperty = jwtTokenProperty;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        if (StringUtils.hasLength(token)) {
            // 验证token有效性
            Authentication authentication = this.getAuthentication(token);
            if (authentication != null) {
                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            }
        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        // 获取token信息
        String token = request.getHeaders().getFirst(jwtTokenProperty.getHeader());
        if (StringUtils.hasLength(token)) {
            return token;
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtUtils.parseToken(token);
        Object o = claims.get("user-info");
        if (ObjectUtils.isEmpty(claims) || ObjectUtils.isEmpty(o)) {
            return null;
        }
        String jsonString = JSON.toJSONString(o);
        AuthUserDetails userInfo = JSON.parseObject(jsonString, AuthUserDetails.class);
        User principal;
        List<GrantedAuthority> grantedAuthorities;
        if (ObjectUtils.isEmpty(userInfo)) {
            return null;
        } else {
            grantedAuthorities = AuthorityUtils.createAuthorityList(userInfo.getRoles());
            principal = new User(userInfo.getUsername(), userInfo.getPassword(), grantedAuthorities);
            return new UsernamePasswordAuthenticationToken(principal, token, grantedAuthorities);
        }
    }
}
