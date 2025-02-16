package com.italycalibur.ciallo.common.models.mapper;

import com.italycalibur.ciallo.common.models.entity.GoodsMasterPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dhr
 * @version 1.0
 * @description: 货主表 Mapper
 * @date 2025-02-16 11:06:13
 */
public interface GoodsMasterMapper extends BaseMapper<GoodsMasterPo> {

    List<GoodsMasterPo> findAllByMod(@Param("shardingIndex") int shardingIndex, @Param("shardingTotal") int shardingTotal);
}

