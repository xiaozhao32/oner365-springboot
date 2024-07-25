package com.oner365.queue.service.mqtt.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.constants.MqttConstants;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.mqtt.IMqttReceiverTaskLogService;

/**
 * MQTT 接收实现
 * 
 * @author zhaoyong
 *
 */
@Service
@Conditional(MqttCondition.class)
public class MqttReceiverTaskLogServiceImpl implements IMqttReceiverTaskLogService {

  private final Logger logger = LoggerFactory.getLogger(MqttReceiverTaskLogServiceImpl.class);

  @Resource
  private ISysTaskLogService sysTaskLogService;
  
  @Override
  @ServiceActivator(
      inputChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, 
      outputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.SAVE_TASK_LOG_QUEUE_NAME
  )
  public void message(Object message) {
    logger.info("Mqtt receive saveExecuteTaskLog: {}", message);
    
    // business
    SysTaskDto sysTask = JSON.parseObject(message.toString(), SysTaskDto.class);
    if (sysTask != null) {
      saveExecuteTaskLog(sysTask);
    }
  }
  
  public void saveExecuteTaskLog(SysTaskDto sysTask) {
    logger.info("saveExecuteTaskLog :{}", sysTask);
    
    long time = System.currentTimeMillis();
    SysTaskLogVo taskLog = new SysTaskLogVo();
    taskLog.setExecuteIp(HttpClientUtils.getLocalhost());
    taskLog.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
    taskLog.setStatus(TaskStatusEnum.NORMAL);
    taskLog.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
    taskLog.setTaskGroup(sysTask.getTaskGroup());
    taskLog.setTaskName(sysTask.getTaskName());
    taskLog.setInvokeTarget(sysTask.getInvokeTarget());
    taskLog.setCreateUser(sysTask.getCreateUser());
    sysTaskLogService.addTaskLog(taskLog);
  }

}
