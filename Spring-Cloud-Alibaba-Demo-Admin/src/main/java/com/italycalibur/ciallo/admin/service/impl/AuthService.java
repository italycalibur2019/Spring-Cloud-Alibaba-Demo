package com.italycalibur.ciallo.admin.service.impl;

import com.italycalibur.ciallo.admin.dto.RegisterDTO;
import com.italycalibur.ciallo.admin.dto.ResetPasswordDTO;
import com.italycalibur.ciallo.admin.service.IAuthService;
import com.italycalibur.ciallo.common.utils.MD5Utils;
import com.italycalibur.ciallo.common.models.entity.UserPO;
import com.italycalibur.ciallo.common.models.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 17:05:39
 * @description: TODO
 */
@Service
public class AuthService implements IAuthService {
    @Resource
    private UserMapper userMapper;

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

    @Override
    public String resetPassword(ResetPasswordDTO params) {
        UserPO user = userMapper.selectByUsername(params.getUsername());
        if (ObjectUtils.isEmpty(user)) {
            return "用户名" + params.getUsername() + "不存在！";
        }
        if (!params.getConfirmPassword().equals(params.getPassword())) {
            return "两次输入的密码不一致！";
        }
        user.setPassword(MD5Utils.md5Encode(params.getPassword()));
        userMapper.updateById(user);
        return "";
    }
}
