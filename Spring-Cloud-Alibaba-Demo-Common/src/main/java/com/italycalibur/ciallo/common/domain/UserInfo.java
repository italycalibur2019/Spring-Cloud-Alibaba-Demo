package com.italycalibur.ciallo.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-14 02:42:25
 * @description: 用户信息展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Long id;

    private String username;

    private String roles;
}
