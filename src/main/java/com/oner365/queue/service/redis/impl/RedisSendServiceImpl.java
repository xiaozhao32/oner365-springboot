package com.oner365.queue.service.redis.impl;

import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.util.GsonUtils;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.RedisCondition;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;

import jakarta.annotation.Resource;

/**
 * Redis pub/subscribe Subscribe Service
 * 
 * @author zhaoyong
 * 
 */
@Service
@Conditional(RedisCondition.class)
public class RedisSendServiceImpl implements IQueueSendService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void sendMessage(String data) {
        redisTemplate.convertAndSend(QueueConstants.MESSAGE_QUEUE_TYPE, data);
    }

    @Override
    public void syncRoute() {
        redisTemplate.convertAndSend(QueueConstants.ROUTE_QUEUE_TYPE, HttpClientUtils.getLocalhost());
    }

    @Override
    public void pullTask(InvokeParamDto data) {
        redisTemplate.convertAndSend(QueueConstants.SCHEDULE_TASK_QUEUE_TYPE, GsonUtils.objectToJson(data, InvokeParamDto.class));
    }

    @Override
    public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
        redisTemplate.convertAndSend(QueueConstants.TASK_UPDATE_STATUS_QUEUE_TYPE, GsonUtils.objectToJson(data, UpdateTaskExecuteStatusDto.class));
    }

    @Override
    public void saveExecuteTaskLog(SysTaskDto data) {
        redisTemplate.convertAndSend(QueueConstants.SAVE_TASK_LOG_QUEUE_TYPE, GsonUtils.objectToJson(data, SysTaskDto.class));
    }

}
