package com.italycalibur.ciallo.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: 鉴权放行路径配置
 * @author dhr
 * @date 2025-02-11 11:07:12
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "secure.whitelist")
public class SecureUrlProperty {
    private String[] urls;
}
