package com.italycalibur.ciallo.admin.client;

import com.italycalibur.ciallo.common.models.entity.GoodsPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-07 21:42:15
 * @description: 测试消费接口
 */
@FeignClient(value = "basic-service")
public interface TestApiFeignClient {
    @PostMapping("/application/basic/api/testApi")
    String testApi(@RequestBody GoodsPO goods);
}
