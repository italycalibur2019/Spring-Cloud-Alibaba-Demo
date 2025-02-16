package com.italycalibur.ciallo.task.handler.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.italycalibur.ciallo.common.exception.CialloException;
import com.italycalibur.ciallo.common.models.entity.GoodsMasterPo;
import com.italycalibur.ciallo.common.models.mapper.GoodsMasterMapper;
import com.italycalibur.ciallo.task.handler.ISendMessageHandler;
import com.xxl.job.core.context.XxlJobHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-16 11:14:45
 * @description: 模拟发送消息任务业务实现
 */
@Slf4j
@Service
public class SendMessageHandler implements ISendMessageHandler {
    @Resource
    private GoodsMasterMapper goodsMasterMapper;

    @Override
    public void sendMessage() {
        log.info(">>>>>>>>>> 任务开始时间：{}", new Date());
        int shardTotal = XxlJobHelper.getShardTotal();
        int shardIndex = XxlJobHelper.getShardIndex();
        List<GoodsMasterPo> goodsMasterPos;
        if (shardTotal == 1) {
            goodsMasterPos = goodsMasterMapper.selectList(Wrappers.emptyWrapper());
        }else {
            // 分片查询
            goodsMasterPos = goodsMasterMapper.findAllByMod(shardIndex, shardTotal);
        }
        log.info("待处理任务数量：{}", goodsMasterPos.size());
        long startTime = System.currentTimeMillis();
        goodsMasterPos.forEach(item -> {
            try {
                // 模拟耗时
                TimeUnit.MILLISECONDS.sleep(10);
                log.info("尊敬的{}：祝您新春佳节，阖家欢乐！", item.getMasterName());
            } catch (InterruptedException e) {
                throw new CialloException("发送失败！", e);
            }
        });
        log.info(">>>>>>>>>> 任务结束时间：{}", new Date());
        log.info(">>>>>>>>>> 任务耗时：{}毫秒", System.currentTimeMillis() - startTime);
    }
}
