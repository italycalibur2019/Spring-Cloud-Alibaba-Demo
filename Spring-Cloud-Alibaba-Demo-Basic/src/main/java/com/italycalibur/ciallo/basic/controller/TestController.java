package com.italycalibur.ciallo.basic.controller;

import com.italycalibur.ciallo.common.domain.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试页面
 * @author dhr
 * @date 2025-02-06 14:56:26
 * @version 1.0
 */
@RestController
@RequestMapping("/application/basic")
public class TestController {
    @Value("${spring.application.name}")
    private String serviceName;

    @RequestMapping("/test")
    public Result<String> test() {
        return Result.ok("Hello, This is " + serviceName + " service! ");
    }
}
