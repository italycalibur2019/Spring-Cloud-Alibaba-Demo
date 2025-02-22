package com.italycalibur.ciallo.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description: 注册参数
 * @author dhr
 * @date 2025-02-11 11:23:15
 * @version 1.0
 */
@Data
@Schema(name = "注册参数")
public class RegisterDTO {
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
