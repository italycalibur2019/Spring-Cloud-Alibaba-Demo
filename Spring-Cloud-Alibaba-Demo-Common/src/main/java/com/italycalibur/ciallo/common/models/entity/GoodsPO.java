package com.italycalibur.ciallo.common.models.entity;

import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-07 20:56:46
 * @description: 货物表
 */
@Data
@NoArgsConstructor
public class GoodsPO {
    private Long id;

    private String goodsName;
}
