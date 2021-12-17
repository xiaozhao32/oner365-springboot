package com.oner365.monitor.rabbitmq.impl;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.api.rabbitmq.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.dto.SysTaskLogDto;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.rabbitmq.IScheduleSendExecuteService;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.InvokeParamVo;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.monitor.vo.SysTaskVo;

/**
 * ScheduleExecute实现类
 * 
 * @author zhaoyong
 */
@Service
public class ScheduleSendExecuteServiceImpl implements IScheduleSendExecuteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleSendExecuteServiceImpl.class);

  @Autowired
  private ISysTaskLogService sysTaskLogService;

  @Autowired
  private ISysTaskService sysTaskService;

  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto updateTask) throws SchedulerException, TaskException {
    LOGGER.info("updateTaskExecuteStatus :{}", updateTask);
    SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
    if (sysTask != null) {
      sysTask.setExecuteStatus(updateTask.getExecuteStatus());
      sysTaskService.save(toVo(sysTask));
    }
  }

  private SysTaskVo toVo(SysTaskDto dto) {
    SysTaskVo result = new SysTaskVo();
    result.setId(dto.getId());
    result.setConcurrent(dto.getConcurrent());
    result.setCreateTime(dto.getCreateTime());
    result.setCreateUser(dto.getCreateUser());
    result.setCronExpression(dto.getCronExpression());
    result.setExecuteStatus(dto.getExecuteStatus());
    result.setInvokeParamVo(toPojo(dto.getInvokeParamDto()));
    result.setInvokeTarget(dto.getInvokeTarget());
    result.setMisfirePolicy(dto.getMisfirePolicy());
    result.setRemark(dto.getRemark());
    result.setStatus(dto.getStatus());
    result.setTaskGroup(dto.getTaskGroup());
    result.setTaskName(dto.getTaskName());
    result.setUpdateTime(dto.getUpdateTime());
    return result;
  }

  private InvokeParamVo toPojo(InvokeParamDto vo) {
    InvokeParamVo result = new InvokeParamVo();
    result.setConcurrent(vo.getConcurrent());
    result.setTaskId(vo.getTaskId());
    result.setTaskParam(vo.getTaskParam());
    result.setTaskServerName(vo.getTaskServerName());
    return result;
  }

  @Override
  public void saveExecuteTaskLog(SysTaskLogDto taskLog) {
    LOGGER.info("saveExecuteTaskLog :{}", taskLog);
    SysTaskDto sysTask = sysTaskService.selectTaskById(taskLog.getTaskId());
    SysTaskLogVo sysTaskLog = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(taskLog)), SysTaskLogVo.class);
    if (sysTask != null) {
      sysTaskLog.setTaskGroup(sysTask.getTaskGroup());
      sysTaskLog.setTaskName(sysTask.getTaskName());
      sysTaskLog.setInvokeTarget(sysTask.getInvokeTarget());
      sysTaskLogService.addTaskLog(sysTaskLog);
    }
  }

}
