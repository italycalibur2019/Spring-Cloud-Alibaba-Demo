package com.italycalibur.ciallo.task.configuration;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@Slf4j
@Configuration
public class XxlJobConfig {

    @Resource
    private XxlJobProperty property;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(String.valueOf(property.getAdmin().get("addresses")));
        xxlJobSpringExecutor.setAppname(String.valueOf(property.getExecutor().get("appname")));
        xxlJobSpringExecutor.setAddress(String.valueOf(property.getExecutor().get("address")));
        xxlJobSpringExecutor.setIp(String.valueOf(property.getExecutor().get("ip")));
        xxlJobSpringExecutor.setPort(Integer.parseInt(String.valueOf(property.getExecutor().get("port"))));
        xxlJobSpringExecutor.setAccessToken(String.valueOf(property.getAdmin().get("accessToken")));
        xxlJobSpringExecutor.setTimeout(Integer.parseInt(String.valueOf(property.getAdmin().get("timeout"))));
        xxlJobSpringExecutor.setLogPath(String.valueOf(property.getExecutor().get("logpath")));
        xxlJobSpringExecutor.setLogRetentionDays(Integer.parseInt(String.valueOf(property.getExecutor().get("logretentiondays"))));

        return xxlJobSpringExecutor;
    }
}