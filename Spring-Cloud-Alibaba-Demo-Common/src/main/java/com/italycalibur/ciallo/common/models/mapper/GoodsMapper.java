package com.italycalibur.ciallo.common.models.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.italycalibur.ciallo.common.models.entity.GoodsPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dhr
 * @version 1.0
 * @description: 货物表 Mapper
 * @date 2025-02-08 13:06:13
 */
@Mapper
public interface GoodsMapper extends BaseMapper<GoodsPO> {
}
