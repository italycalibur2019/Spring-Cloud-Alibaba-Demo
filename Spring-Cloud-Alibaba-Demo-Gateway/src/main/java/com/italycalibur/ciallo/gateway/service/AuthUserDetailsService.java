package com.italycalibur.ciallo.gateway.service;

import com.italycalibur.ciallo.common.models.entity.UserPO;
import com.italycalibur.ciallo.common.models.mapper.UserMapper;
import com.italycalibur.ciallo.gateway.user.AuthUserDetails;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-14 01:46:27
 * @description: 自定义用户信息查询服务
 */
@Service
public class AuthUserDetailsService implements ReactiveUserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        UserPO userPO = userMapper.selectByUsername(username);
        AuthUserDetails user = new AuthUserDetails();
        user.setId(userPO.getId());
        user.setUsername(userPO.getUsername());
        user.setPassword(userPO.getPassword());
        String[] roles = {"admin"};
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add((GrantedAuthority) () -> role);
        }
        user.setAuthorities(authorities);
        return Mono.just(user);
    }
}
