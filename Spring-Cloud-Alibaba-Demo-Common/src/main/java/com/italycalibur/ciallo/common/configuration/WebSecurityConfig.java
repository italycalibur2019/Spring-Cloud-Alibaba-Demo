package com.italycalibur.ciallo.common.configuration;

import com.italycalibur.ciallo.common.configuration.properties.SecureUrlProperty;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-13 23:12:46
 * @description: spring security配置类
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@ConditionalOnProperty(name = "config.gateway.enabled", havingValue = "true")
public class WebSecurityConfig {
    @Resource
    private SecureUrlProperty secureUrlProperty;

    @Bean
    public SecurityWebFilterChain defaultSecurityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(authorize -> authorize
                        .pathMatchers(secureUrlProperty.getUrls()).permitAll()
                        .anyExchange().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2Resource -> oauth2Resource
                        .jwt(Customizer.withDefaults()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .build();
    }
}
