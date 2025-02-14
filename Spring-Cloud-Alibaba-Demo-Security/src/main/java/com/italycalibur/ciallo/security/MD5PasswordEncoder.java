package com.italycalibur.ciallo.security;

import com.italycalibur.ciallo.common.utils.MD5Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-13 23:44:50
 * @description: 自定义MD5密码加密解密组件
 */
@Component
public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Utils.md5Encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MD5Utils.md5Decode(rawPassword, encodedPassword);
    }
}
