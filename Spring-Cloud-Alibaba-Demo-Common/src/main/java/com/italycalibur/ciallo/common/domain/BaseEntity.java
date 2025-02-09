package com.italycalibur.ciallo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 17:13:05
 * @description: 实体基类
 */
@Data
public abstract class BaseEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
}
