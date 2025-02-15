package com.italycalibur.ciallo.task.service.impl;

import com.italycalibur.ciallo.task.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-15 20:05:27
 * @description: 测试服务实现
 */
@Service
public class TestService implements ITestService {
    @Override
    public String testServiceA() {
        return "This is Method A! ";
    }

    @Override
    public String testServiceB() {
        return "This is Method B! ";
    }
}
