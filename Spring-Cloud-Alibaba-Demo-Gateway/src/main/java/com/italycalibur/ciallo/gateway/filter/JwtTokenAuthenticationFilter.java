package com.italycalibur.ciallo.gateway.filter;

import cn.hutool.core.convert.Convert;
import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.exception.CialloException;
import com.italycalibur.ciallo.common.utils.JwtUtils;
import com.italycalibur.ciallo.gateway.security.service.AuthUserDetailsService;
import io.jsonwebtoken.Claims;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthUserDetailsService userDetailsService;

    public JwtTokenAuthenticationFilter(JwtTokenProperty jwtTokenProperty, JwtUtils jwtUtils, AuthUserDetailsService userDetailsService) {
        this.jwtTokenProperty = jwtTokenProperty;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        if (!StringUtils.hasLength(token)) {
            throw new CialloException("未获取到有效Token!");
        }
        // 验证token有效性
        Authentication authentication = this.getAuthentication(token);
        if (!ObjectUtils.isEmpty(authentication)) {
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
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
        if (ObjectUtils.isEmpty(claims)) {
            return null;
        }
        String username = Convert.toStr(claims.get("username"), "");
        if (!StringUtils.hasLength(username)) {
            return null;
        }
        Mono<UserDetails> userDetailsMono = userDetailsService.findByUsername(username);
        UserDetails userDetails = userDetailsMono.block();
        User principal;
        if (ObjectUtils.isEmpty(userDetails)) {
            return null;
        } else {
            String roles = Convert.toStr(claims.get("roles"), "");
            if (!StringUtils.hasLength(roles)) {
                return null;
            }
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
            principal = new User(userDetails.getUsername(), userDetails.getPassword(), grantedAuthorities);
            return new UsernamePasswordAuthenticationToken(principal, token, grantedAuthorities);
        }
    }
}
