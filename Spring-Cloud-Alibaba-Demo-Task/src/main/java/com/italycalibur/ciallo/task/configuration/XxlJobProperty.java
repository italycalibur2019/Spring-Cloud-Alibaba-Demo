package com.italycalibur.ciallo.task.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-15 17:15:55
 * @description: xxl-job 配置文件
 */
@Data
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperty {
    private Map<String, Object> admin;
    private Map<String, Object> executor;
}
