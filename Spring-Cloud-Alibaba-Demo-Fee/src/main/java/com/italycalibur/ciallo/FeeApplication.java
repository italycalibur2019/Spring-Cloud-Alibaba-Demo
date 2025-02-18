package com.italycalibur.ciallo;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot3.autoconfigure.properties.DruidStatProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.italycalibur.ciallo.common.models.mapper")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.italycalibur.ciallo.**")
@EnableConfigurationProperties({DruidStatProperties.class, DataSourceProperties.class})
public class FeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeeApplication.class, args);
    }

}
