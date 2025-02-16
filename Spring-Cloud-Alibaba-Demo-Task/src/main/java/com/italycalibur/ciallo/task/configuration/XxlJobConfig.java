package com.italycalibur.ciallo.task.configuration;

import cn.hutool.core.convert.Convert;
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
        xxlJobSpringExecutor.setAdminAddresses(Convert.toStr(property.getAdmin().get("addresses")));
        xxlJobSpringExecutor.setAppname(Convert.toStr(property.getExecutor().get("appname")));
        xxlJobSpringExecutor.setAddress(Convert.toStr(property.getExecutor().get("address")));
        xxlJobSpringExecutor.setIp(Convert.toStr(property.getExecutor().get("ip")));
        xxlJobSpringExecutor.setPort(Convert.toInt(property.getExecutor().get("port")));
        xxlJobSpringExecutor.setAccessToken(Convert.toStr(property.getAdmin().get("accessToken")));
        xxlJobSpringExecutor.setTimeout(Convert.toInt(property.getAdmin().get("timeout")));
        xxlJobSpringExecutor.setLogPath(Convert.toStr(property.getExecutor().get("logpath")));
        xxlJobSpringExecutor.setLogRetentionDays(Convert.toInt(property.getExecutor().get("logretentiondays")));
        return xxlJobSpringExecutor;
    }
}