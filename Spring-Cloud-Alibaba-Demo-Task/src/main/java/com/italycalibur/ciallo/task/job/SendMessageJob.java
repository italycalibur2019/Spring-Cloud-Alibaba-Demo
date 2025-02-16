package com.italycalibur.ciallo.task.job;

import com.italycalibur.ciallo.task.handler.ISendMessageHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-16 11:08:03
 * @description: 模拟发送消息任务
 */
@Slf4j
@Component
public class SendMessageJob {
    @Resource
    private ISendMessageHandler handler;
    @XxlJob("sendMessageHandler")
    public void sendMessageHandler() throws Exception {
        log.info("-------------------- 开始向货主发送消息 --------------------");

        handler.sendMessage();

        log.info("-------------------- 结束向货主发送消息 --------------------");
    }
}
