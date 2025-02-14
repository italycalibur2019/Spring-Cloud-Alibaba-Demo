package com.italycalibur.ciallo.admin.controller;

import com.italycalibur.ciallo.admin.client.TestApiFeignClient;
import com.italycalibur.ciallo.common.domain.Result;
import com.italycalibur.ciallo.common.models.entity.GoodsPO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 测试页面
 * @author dhr
 * @date 2025-02-06 14:40:26
 * @version 1.0
 */
@RestController
@RequestMapping("/admin")
@RefreshScope
public class TestController {
    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${config.info}")
    private String configInfo;

    @Resource
    private TestApiFeignClient testApiFeignClient;

    @RequestMapping("/test")
    public Result<String> test() {
        return Result.ok("Hello, This is " + serviceName + " service! ");
    }


    @GetMapping("/getConfigInfo")
    public Result<String> getConfigInfo() {
        return Result.ok(configInfo + " 如果你看到这个说明配置成功了！");
    }

    @PostMapping("/testApi/{goodsName}")
    public Result<String> testApi(@PathVariable("goodsName")String goodsName) {
        GoodsPO goods = new GoodsPO();
        goods.setId(1L);
        goods.setGoodsName(goodsName);
        return Result.ok("调用API成功！" + testApiFeignClient.testApi(goods));
    }
}
