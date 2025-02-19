package com.italycalibur.ciallo.gateway.configuration;

import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.configuration.properties.SecureUrlProperty;
import com.italycalibur.ciallo.common.domain.Result;
import com.italycalibur.ciallo.common.utils.JwtUtils;
import com.italycalibur.ciallo.gateway.filter.JwtTokenAuthenticationFilter;
import com.italycalibur.ciallo.gateway.security.MD5PasswordEncoder;
import com.italycalibur.ciallo.gateway.handler.LoginFailureHandler;
import com.italycalibur.ciallo.gateway.handler.LoginSuccessHandler;
import com.italycalibur.ciallo.gateway.handler.LogoutSuccessHandler;
import com.italycalibur.ciallo.gateway.security.service.AuthUserDetailsService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-13 23:12:46
 * @description: spring security配置类
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Resource
    private SecureUrlProperty secureUrlProperty;

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            AuthUserDetailsService userDetailsService, MD5PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager
                = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler,
            LogoutSuccessHandler logoutSuccessHandler,
            AuthUserDetailsService authUserDetailsService,
            JwtTokenProperty property,
            JwtUtils utils,
            ReactiveAuthenticationManager authenticationManager) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(authenticationManager)
                .exceptionHandling(handler -> handler
                        .authenticationEntryPoint(this::customExceptionHandler)// 自定义认证失败处理器
                        .accessDeniedHandler(this::customExceptionHandler)// 自定义访问拒绝处理器
                )
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(auth -> auth
                        .pathMatchers(secureUrlProperty.getUrls()).permitAll()// 放行白名单
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()// 放行options请求
                        .anyExchange().authenticated()// 其他请求都需要认证
                )
                .formLogin(login -> login
                        .loginPage(secureUrlProperty.getLoginUrl())// 登录路径
                        .authenticationSuccessHandler(loginSuccessHandler)// 登录成功处理器
                        .authenticationFailureHandler(loginFailureHandler)// 登录失败处理器
                )
                .logout(logout -> logout
                        .logoutUrl(secureUrlProperty.getLogoutUrl())// 登出地址
                        .logoutSuccessHandler(logoutSuccessHandler) // 登出成功处理器
                )
                .addFilterAt(new JwtTokenAuthenticationFilter(property, utils, authUserDetailsService), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    private Mono<Void> customExceptionHandler(ServerWebExchange exchange, Object exception) {
        ServerHttpResponse response = exchange.getResponse();
        String result;
        if (exception instanceof AuthenticationException) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            result = Result.unauthorized().asJsonString();
        } else if (exception instanceof AccessDeniedException){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            result = Result.forbidden().asJsonString();
        }else {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            result = Result.error("未知异常").asJsonString();
        }
        return response.writeWith(Mono.just(response.bufferFactory().wrap(result.getBytes())));
    }
}
