package com.italycalibur.ciallo.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.italycalibur.ciallo.basic.service.IGoodsMasterService;
import com.italycalibur.ciallo.common.models.entity.GoodsMasterPO;
import com.italycalibur.ciallo.common.models.mapper.GoodsMasterMapper;
import org.springframework.stereotype.Service;

/**
 * @description: 货主服务实现类
 * @author dhr
 * @date 2025-02-08 13:45:44
 * @version 1.0
 */
@Service
public class GoodsMasterService extends ServiceImpl<GoodsMasterMapper, GoodsMasterPO> implements IGoodsMasterService {
}
