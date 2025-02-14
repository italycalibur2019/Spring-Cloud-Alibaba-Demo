package com.italycalibur.ciallo.security.service;

import com.italycalibur.ciallo.common.models.entity.UserPO;
import com.italycalibur.ciallo.common.models.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
        UserDetails user = User.withUsername(username).password(userPO.getPassword()).roles("admin").authorities("admin").build();
        return Mono.just(user);
    }
}
