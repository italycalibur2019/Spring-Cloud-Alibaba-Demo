package com.italycalibur.ciallo.gateway;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot3.autoconfigure.properties.DruidStatProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.italycalibur.ciallo.common.models.mapper")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableDiscoveryClient
@ComponentScan("com.italycalibur.ciallo.**")
@EnableConfigurationProperties({DruidStatProperties.class, DataSourceProperties.class})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}