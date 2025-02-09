package com.italycalibur.ciallo.admin.service.impl;

import com.italycalibur.ciallo.admin.service.IAuthService;
import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.models.entity.UserPO;
import com.italycalibur.ciallo.common.models.mapper.UserMapper;
import com.italycalibur.ciallo.common.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 17:05:39
 * @description: TODO
 */
@Service
public class AuthService implements IAuthService {
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtTokenProperty jwtTokenProperty;

    /**
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        // 验证账号密码
        UserPO user = userMapper.selectByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return "用户名或密码错误";
        }
        // jwt生成token
        Map<String, Object> claims = Map.of("id", user.getId(), "username", user.getUsername());
        String token = jwtUtils.createToken(claims);
        // 添加前缀
        String headerToken = jwtTokenProperty.getPrefix() + token;
        // redis存储token
        redisTemplate.opsForValue().set(token, headerToken, jwtTokenProperty.getExpireTime(), TimeUnit.MILLISECONDS);
        // 返回token
        return headerToken;
    }
}
