package com.oner365.queue.service.impl;

import java.util.Optional;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.common.enums.StatusEnum;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.dto.SysTaskLogDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.service.IQueueRabbitmqReceiverService;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * rabbitmq 接收队列实现类
 * 
 * @author zhaoyong
 *
 */
@Service
public class QueueRabbitmqReceiverServiceImpl implements IQueueRabbitmqReceiverService {

  protected final Logger logger = LoggerFactory.getLogger(IQueueRabbitmqReceiverService.class);
  
  @Autowired
  private DynamicRouteService dynamicRouteService;
  
  @Autowired
  private ISysTaskLogService sysTaskLogService;

  @Autowired
  private ISysTaskService sysTaskService;

  @Override
  public void message(String message) {
    Optional<String> optional = Optional.ofNullable(message);
    optional.ifPresent(s -> logger.info("Message: {}", s));
  }
  
  @Override
  public void syncRoute(String gatewayIp) {
      logger.info("MQ pull: {}", gatewayIp);
      dynamicRouteService.refreshRoute();
  }

  @Override
  public void scheduleTask(InvokeParamDto invokeParamDto) {
    if (ScheduleConstants.SCHEDULE_SERVER_NAME.equals(invokeParamDto.getTaskServerName())) {
      taskExecute(invokeParamDto.getConcurrent(), invokeParamDto.getTaskId(), invokeParamDto.getTaskParam());
    }
  }
  
  private void taskExecute(String concurrent, String taskId, JSONObject param) {
    StatusEnum status = StatusEnum.YES;
    SysTaskDto sysTask = sysTaskService.selectTaskById(taskId);
    if (sysTask != null) {
      if (ScheduleConstants.SCHEDULE_CONCURRENT.equals(concurrent)) {
        logger.info("taskExecute  concurrent : {} , update sysTask  executeStatus = 0", concurrent);
        status = execute(taskId, param, sysTask);

      } else {
        if (!StatusEnum.NO.equals(sysTask.getExecuteStatus())) {
          status = execute(taskId, param, sysTask);
        }
        logger.info("taskExecute  concurrent : {}", concurrent);
      }
      executeLog(sysTask, status);
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

  private void executeLog(SysTaskDto sysTask, StatusEnum status) {
    logger.info("saveTaskLog status:{}", status);
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
  public void saveExecuteTaskLog(SysTaskLogDto taskLog) {
    logger.info("saveExecuteTaskLog :{}", taskLog);
    SysTaskDto sysTask = sysTaskService.selectTaskById(taskLog.getTaskId());
    SysTaskLogVo sysTaskLog = convert(taskLog, SysTaskLogVo.class);
    if (sysTask != null) {
      sysTaskLog.setTaskGroup(sysTask.getTaskGroup());
      sysTaskLog.setTaskName(sysTask.getTaskName());
      sysTaskLog.setInvokeTarget(sysTask.getInvokeTarget());
      sysTaskLogService.addTaskLog(sysTaskLog);
    }
  }

}
