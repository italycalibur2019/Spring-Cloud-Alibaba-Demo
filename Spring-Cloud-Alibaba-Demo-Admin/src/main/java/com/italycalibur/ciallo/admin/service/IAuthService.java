package com.italycalibur.ciallo.admin.service;

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
}
