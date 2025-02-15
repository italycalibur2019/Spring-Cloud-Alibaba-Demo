package com.italycalibur.ciallo.task.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-15 17:20:27
 * @description: 测试任务
 */
@Component
public class TestJob {
    @Value("${config.info}")
    private String info;

    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        System.out.println(info + "，执行时间：" + new Date());
    }
}
