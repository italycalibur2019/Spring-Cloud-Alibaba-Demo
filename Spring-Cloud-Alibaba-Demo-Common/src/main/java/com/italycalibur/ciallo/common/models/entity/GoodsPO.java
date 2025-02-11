package com.italycalibur.ciallo.common.models.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.italycalibur.ciallo.common.domain.BaseEntity;
import lombok.*;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-07 20:56:46
 * @description: 货物表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_goods_po", schema = "basic")
public class GoodsPO extends BaseEntity {
    @TableField(value = "goods_code")
    private String goodsCode;

    @TableField(value = "goods_name")
    private String goodsName;
}
