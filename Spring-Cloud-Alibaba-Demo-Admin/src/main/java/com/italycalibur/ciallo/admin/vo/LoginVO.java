package com.italycalibur.ciallo.admin.vo;

import com.italycalibur.ciallo.common.domain.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 登录返回结果
 * @author dhr
 * @date 2025-02-11 11:23:42
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private String token;
    private UserInfo userInfo;
}
