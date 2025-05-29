package com.oner365.queue.service.mqtt.impl;

import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.constants.MqttConstants;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.mqtt.IMqttReceiverTaskExecuteStatusService;

/**
 * MQTT 接收实现
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(MqttCondition.class)
public class MqttReceiverTaskExecuteStatusServiceImpl implements IMqttReceiverTaskExecuteStatusService {

    private final Logger logger = LoggerFactory.getLogger(MqttReceiverTaskExecuteStatusServiceImpl.class);

    @Resource
    private ISysTaskService sysTaskService;

    @Override
    @ServiceActivator(inputChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME,
            outputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME)
    public void message(Object message) {
        logger.info("Mqtt receive setExecuteStatus: {}", message);

        // business
        UpdateTaskExecuteStatusDto updateTask = JSON.parseObject(message.toString(), UpdateTaskExecuteStatusDto.class);
        if (updateTask != null) {
            SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
            if (sysTask != null) {
                sysTask.setExecuteStatus(updateTask.getExecuteStatus());
                try {
                    sysTaskService.save(convert(sysTask, SysTaskVo.class));
                }
                catch (Exception e) {
                    logger.error("save error", e);
                }
            }
        }
    }

}
