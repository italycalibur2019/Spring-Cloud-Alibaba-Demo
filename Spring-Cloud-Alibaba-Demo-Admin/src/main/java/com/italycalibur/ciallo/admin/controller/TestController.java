package com.italycalibur.ciallo.admin.controller;

import com.italycalibur.ciallo.common.domain.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试页面
 * @author dhr
 * @date 2025-02-06 14:40:26
 * @version 1.0
 */
@RestController
@RequestMapping("/application/admin")
@RefreshScope
public class TestController {
    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${config.info}")
    private String configInfo;

    @RequestMapping("/test")
    public Result<String> test() {
        return Result.ok("Hello, This is " + serviceName + " service! ");
    }


    @GetMapping("/getConfigInfo")
    public Result<String> getConfigInfo() {
        return Result.ok(configInfo + " 如果你看到这个说明配置成功了！");
    }
}
