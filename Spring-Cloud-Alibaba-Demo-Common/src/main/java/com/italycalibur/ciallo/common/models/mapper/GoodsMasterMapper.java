package com.italycalibur.ciallo.common.models.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.italycalibur.ciallo.common.models.entity.GoodsMasterPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dhr
 * @version 1.0
 * @description: 货主表 Mapper
 * @date 2025-02-16 11:06:13
 */
@DS("basic")
public interface GoodsMasterMapper extends BaseMapper<GoodsMasterPO> {

    List<GoodsMasterPO> findAllByMod(@Param("shardingIndex") int shardingIndex, @Param("shardingTotal") int shardingTotal);
}

