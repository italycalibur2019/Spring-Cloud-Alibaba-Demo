package com.italycalibur.ciallo.task.handler.impl;

import com.italycalibur.ciallo.task.handler.ITestHandler;
import org.springframework.stereotype.Service;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-15 20:05:27
 * @description: 测试服务实现
 */
@Service
public class TestHandler implements ITestHandler {
    @Override
    public String testMethodA() {
        return "This is Method A! ";
    }

    @Override
    public String testMethodB() {
        return "This is Method B! ";
    }
}
