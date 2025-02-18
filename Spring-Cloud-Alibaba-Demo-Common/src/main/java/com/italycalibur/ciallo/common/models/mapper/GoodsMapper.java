package com.italycalibur.ciallo.common.models.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.italycalibur.ciallo.common.models.entity.GoodsPO;
import org.apache.ibatis.annotations.Select;

/**
 * @author dhr
 * @version 1.0
 * @description: 货物表 Mapper
 * @date 2025-02-08 13:06:13
 */
@DS("basic")
public interface GoodsMapper extends BaseMapper<GoodsPO> {
    /**
     * 根据用户名查询用户信息
     * @param goodsCode 货物编号
     * @return GoodsPO
     */
    @Select("select * from basic.t_goods_po where goods_code = #{goodsCode}")
    GoodsPO selectByGoodsCode(String goodsCode);
}
