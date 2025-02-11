package com.italycalibur.ciallo.common.models.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.italycalibur.ciallo.common.domain.BaseEntity;
import lombok.*;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-07 20:56:46
 * @description: 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_user_po", schema = "sys")
public class UserPO extends BaseEntity {
    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;
}
