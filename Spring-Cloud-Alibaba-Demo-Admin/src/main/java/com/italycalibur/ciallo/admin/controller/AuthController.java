package com.italycalibur.ciallo.admin.controller;

import com.italycalibur.ciallo.admin.dto.LoginDTO;
import com.italycalibur.ciallo.admin.service.IAuthService;
import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.domain.Result;
import jakarta.annotation.Resource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 16:23:38
 * @description: 访问管理
 */
@RestController
@RequestMapping("/application/admin/auth")
@RefreshScope
public class AuthController {
    @Resource
    private IAuthService authService;
    @Resource
    private JwtTokenProperty jwtTokenProperty;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO params) {
        String result = authService.login(params.getUsername(), params.getPassword());
        if (result == null || !result.contains(jwtTokenProperty.getPrefix())) {
            return Result.error(500, "登录失败：" + result);
        }
        return Result.ok(result);
    }
}
