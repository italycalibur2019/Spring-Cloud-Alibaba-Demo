package com.italycalibur.ciallo.common.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 16:31:29
 * @description: jwt配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtTokenProperty {
    /**
     * 密钥
     */
    private String secretKey;
    /**
     * 过期时间（24小时）
     */
    private long expireTime;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 头文件名
     */
    private String header;
}
