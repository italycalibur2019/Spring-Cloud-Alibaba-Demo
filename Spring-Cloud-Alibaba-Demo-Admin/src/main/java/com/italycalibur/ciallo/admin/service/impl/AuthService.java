package com.italycalibur.ciallo.admin.service.impl;

import com.italycalibur.ciallo.admin.dto.RegisterDTO;
import com.italycalibur.ciallo.admin.service.IAuthService;
import com.italycalibur.ciallo.common.domain.user.UserInfo;
import com.italycalibur.ciallo.common.utils.MD5Utils;
import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.models.entity.UserPO;
import com.italycalibur.ciallo.common.models.mapper.UserMapper;
import com.italycalibur.ciallo.common.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    @Override
    public String login(String username, String password) {
        // 验证账号密码
        UserPO user = userMapper.selectByUsername(username);
        if (ObjectUtils.isEmpty(user) || !MD5Utils.md5Decode(password,user.getPassword())) {
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

    /**
     * 从token获取用户信息
     * @param token 登录令牌
     * @return UserInfo
     */
    @Override
    public UserInfo getUserInfo(String token) {
        String username = jwtUtils.parseToken(token).get("username").toString();
        if (ObjectUtils.isEmpty(username)) {
            return null;
        }
        return new UserInfo(username);
    }

    /**
     * 注册
     * @param params 注册信息
     * @return result
     */
    @Override
    public String register(RegisterDTO params) {
        UserPO user = userMapper.selectByUsername(params.getUsername());
        if (!ObjectUtils.isEmpty(user)) {
            return "用户名" + params.getUsername() + "已存在！";
        }
        userMapper.insert(new UserPO(params.getUsername(), MD5Utils.md5Encode(params.getPassword())));
        return "";
    }
}
