package com.italycalibur.ciallo.admin.controller;

import com.italycalibur.ciallo.admin.dto.RegisterDTO;
import com.italycalibur.ciallo.admin.dto.ResetPasswordDTO;
import com.italycalibur.ciallo.admin.service.IAuthService;
import com.italycalibur.ciallo.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * @description: 用户操作相关
 */
@Tag(name = "用户操作相关")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private IAuthService authService;

    @Operation(summary = "注册用户")
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO params) {
        String result = authService.register(params);
        if (StringUtils.hasLength(result)) {
            return Result.error("注册失败：" + result);
        }
        return Result.ok("注册成功！");
    }

    @Operation(summary = "重置密码")
    @PostMapping("/resetPassword")
    public Result<String> resetPassword(@RequestBody ResetPasswordDTO params) {
        String result = authService.resetPassword(params);
        if (StringUtils.hasLength(result)) {
            return Result.error("重置密码失败：" + result);
        }
        return Result.ok("重置密码成功！");
    }
}
