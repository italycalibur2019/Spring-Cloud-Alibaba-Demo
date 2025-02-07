package com.italycalibur.ciallo.basic.api;

import com.italycalibur.ciallo.common.models.entity.GoodsPO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-07 21:28:33
 * @description: 测试提供接口
 */
@RestController
@RequestMapping("/application/basic/api")
public class TestApi {
    @Value("${config.name}")
    private String configName;

    @PostMapping("/testApi")
    public String testApi(@RequestBody GoodsPO goods) {
        return "调用" + configName + "API成功！货物名称：" + goods.getGoodsName();
    }
}
