package com.italycalibur.ciallo.admin.dto;

import lombok.Data;

/**
 * @description: 重置密码参数
 * @author dhr
 * @date 2025-02-11 11:23:15
 * @version 1.0
 */
@Data
public class ResetPasswordDTO {
    private String username;
    private String password;
    private String confirmPassword;
}
