package com.italycalibur.ciallo.admin.controller;

import com.italycalibur.ciallo.admin.dto.LoginDTO;
import com.italycalibur.ciallo.admin.dto.RegisterDTO;
import com.italycalibur.ciallo.admin.service.IAuthService;
import com.italycalibur.ciallo.admin.vo.LoginVO;
import com.italycalibur.ciallo.common.constants.ResultCode;
import com.italycalibur.ciallo.common.domain.user.UserInfo;
import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import com.italycalibur.ciallo.common.domain.Result;
import jakarta.annotation.Resource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.ObjectUtils;
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
@RequestMapping("/application/admin/auth")
@RefreshScope
public class AuthController {
    @Resource
    private IAuthService authService;
    @Resource
    private JwtTokenProperty jwtTokenProperty;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO params) {
        // 获取token
        String token = authService.login(params.getUsername(), params.getPassword());
        if (ObjectUtils.isEmpty(token) || !token.contains(jwtTokenProperty.getPrefix())) {
            return Result.error(ResultCode.FAILURE, "登录失败：" + token);
        }
        // 解析用户信息
        LoginVO loginVo = new LoginVO();
        loginVo.setToken(token);
        UserInfo userInfo = authService.getUserInfo(token);
        if (ObjectUtils.isEmpty(userInfo)) {
            return Result.error(ResultCode.FAILURE, "登录失败：用户信息解析有误");
        }
        loginVo.setUserInfo(userInfo);

        return Result.ok(loginVo);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO params) {
        String result = authService.register(params);
        if (StringUtils.hasLength(result)) {
            return Result.error(ResultCode.FAILURE, "注册失败：" + result);
        }
        return Result.ok("注册成功！");
    }
}
