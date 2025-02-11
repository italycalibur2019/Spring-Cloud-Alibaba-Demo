package com.italycalibur.ciallo.admin.service;

import com.italycalibur.ciallo.admin.dto.RegisterDTO;
import com.italycalibur.ciallo.common.domain.user.UserInfo;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 17:04:56
 * @description: TODO
 */
public interface IAuthService {
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return String
     */
    String login(String username, String password);

    /**
     * 从token获取用户信息
     * @param token 登录令牌
     * @return UserInfo
     */
    UserInfo getUserInfo(String token);

    /**
     * 注册
     * @param params 注册信息
     * @return 结果
     */
    String register(RegisterDTO params);
}
