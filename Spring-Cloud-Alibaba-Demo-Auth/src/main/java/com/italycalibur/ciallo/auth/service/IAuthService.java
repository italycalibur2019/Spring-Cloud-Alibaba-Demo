package com.italycalibur.ciallo.auth.service;

import com.italycalibur.ciallo.auth.dto.RegisterDTO;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 17:04:56
 * @description: TODO
 */
public interface IAuthService {
    /**
     * 注册
     * @param params 注册信息
     * @return 结果
     */
    String register(RegisterDTO params);
}
