package com.italycalibur.ciallo.admin.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: swagger 作者信息配置
 * @author dhr
 * @date 2025-02-22 08:19:25
 * @version 1.0
 */
@Configuration
public class AdminSwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        // 建造者模式构建Docket
        return GroupedOpenApi.builder()
                .addOpenApiCustomizer(openApi -> openApi.setInfo(apiInfo()))
                .group("管理模块")
                .packagesToScan("com.italycalibur.ciallo.admin")
                .build();
    }

    private Info apiInfo() {
        return new Info()
                .title("系统模块-接口文档")
                .description("微服务架构-系统管理模块")
                .contact(new Contact().url("https://github.com/italycalibur2019")
                        .name("italycalibur")
                        .email("920893925@qq.com"))
                .license(new License().name("MIT License")
                        .identifier("MIT")
                        .url("https://mit-license.org"))
                .version("v1.0");
    }
}
