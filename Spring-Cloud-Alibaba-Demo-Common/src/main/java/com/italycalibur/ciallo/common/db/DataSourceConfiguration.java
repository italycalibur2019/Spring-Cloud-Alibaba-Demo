package com.italycalibur.ciallo.common.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @description: 多数据源配置类
 * @author dhr
 * @date 2025-02-11 15:02:32
 * @version 1.0
 */
@Configuration
public class DataSourceConfiguration {
    @Bean("dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.druid.datasource1")
    public DataSource dataSource1() {
        return new DruidDataSource();
    }

    @Bean("dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.druid.datasource2")
    public DataSource dataSource2() {
        return new DruidDataSource();
    }

    @Bean("dataSource3")
    @ConfigurationProperties(prefix = "spring.datasource.druid.datasource3")
    public DataSource dataSource3() {
        return new DruidDataSource();
    }

    @Bean("dataSource4")
    @ConfigurationProperties(prefix = "spring.datasource.druid.datasource4")
    public DataSource dataSource4() {
        return new DruidDataSource();
    }

    @Bean("dataSource5")
    @ConfigurationProperties(prefix = "spring.datasource.druid.datasource5")
    public DataSource dataSource5() {
        return new DruidDataSource();
    }
}
