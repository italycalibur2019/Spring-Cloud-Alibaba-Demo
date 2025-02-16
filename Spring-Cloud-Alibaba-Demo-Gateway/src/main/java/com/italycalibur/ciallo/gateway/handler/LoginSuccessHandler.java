package com.italycalibur.ciallo.gateway.handler;

import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.constants.ResultCode;
import com.italycalibur.ciallo.common.domain.Result;
import com.italycalibur.ciallo.common.exception.CialloException;
import com.italycalibur.ciallo.gateway.service.AuthUserDetailsService;
import com.italycalibur.ciallo.gateway.user.AuthUserDetails;
import com.italycalibur.ciallo.gateway.user.UserInfo;
import com.italycalibur.ciallo.common.utils.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-14 00:18:23
 * @description: 自定义登录成功处理器
 */
@Slf4j
@Component
public class LoginSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {
    @Resource
    private AuthUserDetailsService userDetailsService;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private JwtTokenProperty jwtTokenProperty;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().setCacheControl("no-store,no-cache,must-revalidate,max-age-8");
        try {
            AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();
            UserInfo userInfo = getUserInfo(user.getUsername(), user.getPassword());
            // jwt生成token
            Map<String, Object> claims = Map.of( "id", userInfo.getId(),
                    "username", userInfo.getUsername(),
                    "roles", userInfo.getRoles());
            String token = jwtUtils.createToken(claims);
            // 添加前缀
            String headerToken = jwtTokenProperty.getPrefix() + token;
            // redis存储token
            redisTemplate.opsForValue().set(token, headerToken, jwtTokenProperty.getExpireTime(), TimeUnit.MILLISECONDS);
            Map<String, Object> map = Map.of("userInfo", userInfo, "token", headerToken);
            Result<Map<String, Object>> result = Result.ok(map);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(result.asJsonString().getBytes())));
        } catch (Exception e) {
            log.error("发生异常！", e);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(Result.error(ResultCode.UNAUTHORIZED, e.getMessage()).asJsonString().getBytes())));
        }
    }

    private UserInfo getUserInfo(String username, String password) {
        Mono<UserDetails> userMono = userDetailsService.findByUsername(username);
        AuthUserDetails userDetails = (AuthUserDetails) userMono.block();
        if (ObjectUtils.isEmpty(userDetails) || !password.equals(userDetails.getPassword())) {
            throw new CialloException("用户名或密码错误！");
        }else {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userDetails.getId());
            userInfo.setUsername(userDetails.getUsername());
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            Set<String> roleSet = new HashSet<>();
            for (GrantedAuthority authority : authorities) {
                roleSet.add(authority.getAuthority());
            }
            userInfo.setRoles(String.join(",", roleSet));
            return userInfo;
        }
    }
}
