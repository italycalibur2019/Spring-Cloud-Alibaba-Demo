package com.italycalibur.ciallo.common.models.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.italycalibur.ciallo.common.domain.BaseEntity;
import lombok.*;

/**
 * <p>
 * 货主表
 * </p>
 *
 * @author italycalibur
 * @since 2025-02-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_goods_master_po", schema = "basic")
public class GoodsMasterPO extends BaseEntity {

    private String masterName;

    private String phone;

    private String remark;
}
