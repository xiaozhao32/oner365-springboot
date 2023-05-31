package com.oner365.queue.service.rabbitmq.impl;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.common.enums.StatusEnum;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.RabbitmqCondition;
import com.oner365.queue.service.rabbitmq.IQueueRabbitmqReceiverService;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;
import com.rabbitmq.client.Channel;

/**
 * rabbitmq 接收队列实现类
 * 
 * @author zhaoyong
 *
 */
@Service
@Conditional(RabbitmqCondition.class)
public class QueueRabbitmqReceiverServiceImpl implements IQueueRabbitmqReceiverService {

  private final Logger logger = LoggerFactory.getLogger(QueueRabbitmqReceiverServiceImpl.class);
  
  @Resource
  private DynamicRouteService dynamicRouteService;
  
  @Resource
  private ISysTaskLogService sysTaskLogService;

  @Resource
  private ISysTaskService sysTaskService;

  @Override
  public void message(JSONObject msg, Channel channel, Message message) throws IOException {
    try {
      Optional<JSONObject> optional = Optional.ofNullable(msg);
      optional.ifPresent(s -> logger.info("Message: {}", s));
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    } catch (IOException e) {
      if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {
        logger.info("消息处理失败，拒绝接收.");
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
      } else {
        logger.info("消息重新发送.");
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
      }
    }
  }
  
  @Override
  public void syncRoute(String gatewayIp) {
      logger.info("syncRoute: {}", gatewayIp);
      dynamicRouteService.refreshRoute();
  }

  @Override
  public void scheduleTask(InvokeParamDto invokeParamDto) {
    if (ScheduleConstants.SCHEDULE_SERVER_NAME.equals(invokeParamDto.getTaskServerName())) {
      taskExecute(invokeParamDto.getConcurrent(), invokeParamDto.getTaskId(), invokeParamDto.getTaskParam());
    }
  }
  
  private void taskExecute(String concurrent, String taskId, JSONObject param) {
    SysTaskDto sysTask = sysTaskService.selectTaskById(taskId);
    if (sysTask != null) {
      if (ScheduleConstants.SCHEDULE_CONCURRENT.equals(concurrent)) {
        logger.info("taskExecute  concurrent : {} , update sysTask  executeStatus = 0", concurrent);
        execute(taskId, param, sysTask);

      } else {
        if (!StatusEnum.NO.equals(sysTask.getExecuteStatus())) {
          execute(taskId, param, sysTask);
        }
        logger.info("taskExecute  concurrent : {}", concurrent);
      }
      saveExecuteTaskLog(sysTask);
    }
  }
  
  private StatusEnum execute(String taskId, JSONObject param, SysTaskDto sysTask) {
    try {
      logger.info("taskId:{}", taskId);
      sysTask.setExecuteStatus(StatusEnum.NO);
      sysTaskService.save(convert(sysTask, SysTaskVo.class));
      int day = param.getInteger("day");
      String time = DateUtil.nextDay(day - 2 * day, DateUtil.FULL_TIME_FORMAT);
      sysTaskLogService.deleteTaskLogByCreateTime(time);
      
      sysTask.setExecuteStatus(StatusEnum.YES);
      sysTaskService.save(convert(sysTask, SysTaskVo.class));
      return StatusEnum.YES;
    } catch (Exception e) {
      logger.error("update sysTask Exception:", e);
      return StatusEnum.NO;
    }
  }

  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto updateTask) throws SchedulerException, TaskException {
    logger.info("updateTaskExecuteStatus :{}", updateTask);
    SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
    if (sysTask != null) {
      sysTask.setExecuteStatus(updateTask.getExecuteStatus());
      sysTaskService.save(convert(sysTask, SysTaskVo.class));
    }
  }

  @Override
  public void saveExecuteTaskLog(SysTaskDto sysTask) {
    logger.info("saveExecuteTaskLog :{}", sysTask);
    
    long time = System.currentTimeMillis();
    SysTaskLogVo taskLog = new SysTaskLogVo();
    taskLog.setExecuteIp(DataUtils.getLocalhost());
    taskLog.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
    taskLog.setStatus(TaskStatusEnum.NORMAL);
    taskLog.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
    taskLog.setTaskGroup(sysTask.getTaskGroup());
    taskLog.setTaskName(sysTask.getTaskName());
    taskLog.setInvokeTarget(sysTask.getInvokeTarget());
    sysTaskLogService.addTaskLog(taskLog);
  }

}
