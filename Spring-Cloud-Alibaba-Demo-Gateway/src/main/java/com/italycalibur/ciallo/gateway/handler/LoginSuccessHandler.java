package com.italycalibur.ciallo.gateway.handler;

import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.constants.ResultCode;
import com.italycalibur.ciallo.common.domain.Result;
import com.italycalibur.ciallo.common.exception.CialloException;
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

import java.util.Map;
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
            User user = (User) authentication.getPrincipal();
            AuthUserDetails authUserDetails = getUserDetails(user);
            // jwt生成token
            Map<String, Object> claims = Map.of( "user-info", authUserDetails);
            String token = jwtUtils.createToken(claims);
            // 添加前缀
            String headerToken = jwtTokenProperty.getPrefix() + token;
            // redis存储token
            redisTemplate.opsForValue().set(token, headerToken, jwtTokenProperty.getExpireTime(), TimeUnit.MILLISECONDS);
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(authUserDetails.getUsername());
            userInfo.setRoles(authUserDetails.getRoles());
            Map<String, Object> map = Map.of("user-info", userInfo, "token", headerToken);
            Result<Map<String, Object>> result = Result.ok(map);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(result.asJsonString().getBytes())));
        } catch (Exception e) {
            log.error("发生异常！", e);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(Result.error(ResultCode.UNAUTHORIZED, e.getMessage()).asJsonString().getBytes())));
        }
    }

    private AuthUserDetails getUserDetails(User user) {
        Mono<UserDetails> userMono = userDetailsService.findByUsername(user.getUsername());
        UserDetails userDetails = userMono.block();
        if (ObjectUtils.isEmpty(userDetails) || !user.getPassword().equals(userDetails.getPassword())) {
            throw new CialloException("用户名或密码错误！");
        }else {
            AuthUserDetails userInfo = new AuthUserDetails();
            userInfo.setUsername(user.getUsername());
            userInfo.setPassword(user.getPassword());
            userInfo.setRoles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
            return userInfo;
        }
    }
}
