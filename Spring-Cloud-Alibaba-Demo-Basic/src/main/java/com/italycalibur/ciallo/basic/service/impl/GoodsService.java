package com.italycalibur.ciallo.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.italycalibur.ciallo.basic.service.IGoodsService;
import com.italycalibur.ciallo.common.models.entity.GoodsPO;
import com.italycalibur.ciallo.common.models.mapper.GoodsPOMapper;
import org.springframework.stereotype.Service;

/**
 * @description: 货物服务实现类
 * @author dhr
 * @date 2025-02-08 13:45:44
 * @version 1.0
 */
@Service
public class GoodsService extends ServiceImpl<GoodsPOMapper, GoodsPO> implements IGoodsService {
}
