package com.italycalibur.ciallo.common.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-07 20:56:46
 * @description: 货物表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_goods_po")
public class GoodsPO {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "goods_name")
    private String goodsName;
}
