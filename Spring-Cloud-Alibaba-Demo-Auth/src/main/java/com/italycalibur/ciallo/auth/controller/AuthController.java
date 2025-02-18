package com.italycalibur.ciallo.auth.controller;

import com.italycalibur.ciallo.auth.dto.RegisterDTO;
import com.italycalibur.ciallo.auth.dto.ResetPasswordDTO;
import com.italycalibur.ciallo.auth.service.IAuthService;
import com.italycalibur.ciallo.common.domain.Result;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
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
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private IAuthService authService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO params) {
        String result = authService.register(params);
        if (StringUtils.hasLength(result)) {
            return Result.error("注册失败：" + result);
        }
        return Result.ok("注册成功！");
    }

    @PostMapping("/resetPassword")
    public Result<String> resetPassword(@RequestBody ResetPasswordDTO params) {
        String result = authService.resetPassword(params);
        if (StringUtils.hasLength(result)) {
            return Result.error("重置密码失败：" + result);
        }
        return Result.ok("重置密码成功！");
    }
}
