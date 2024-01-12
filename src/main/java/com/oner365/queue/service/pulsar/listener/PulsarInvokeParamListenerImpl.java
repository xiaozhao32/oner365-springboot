package com.oner365.queue.service.pulsar.listener;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.service.BaseService;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * pulsar InvokeParamDto listener
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(PulsarCondition.class)
public class PulsarInvokeParamListenerImpl implements MessageListener<InvokeParamDto>, BaseService {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(PulsarInvokeParamListenerImpl.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Resource
  private ISysTaskLogService sysTaskLogService;

  @Resource
  private ISysTaskService sysTaskService;

  @Override
  public void received(Consumer<InvokeParamDto> consumer, Message<InvokeParamDto> msg) {
    try {
      String data = Arrays.toString(msg.getData());
      LOGGER.info("Pulsar consumer data: {}, topic: {}", data, consumer.getTopic());
      consumer.acknowledge(msg);
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
    // business
    InvokeParamDto dto = msg.getValue();
    if (dto != null && ScheduleConstants.SCHEDULE_SERVER_NAME.equals(dto.getTaskServerName())) {
      taskExecute(dto.getConcurrent(), dto.getTaskId(), dto.getTaskParam());
    }

  }

  private void taskExecute(String concurrent, String taskId, JSONObject param) {
    SysTaskDto sysTask = sysTaskService.selectTaskById(taskId);
    if (sysTask != null) {
      if (ScheduleConstants.SCHEDULE_CONCURRENT.equals(concurrent)) {
        LOGGER.info("taskExecute  concurrent : {} , update sysTask  executeStatus = 0", concurrent);
        execute(taskId, param, sysTask);

      } else {
        if (!StatusEnum.NO.equals(sysTask.getExecuteStatus())) {
          execute(taskId, param, sysTask);
        }
        LOGGER.info("taskExecute  concurrent : {}", concurrent);
      }
      saveExecuteTaskLog(sysTask);
    }
  }

  private StatusEnum execute(String taskId, JSONObject param, SysTaskDto sysTask) {
    try {
      LOGGER.info("taskId:{}", taskId);
      sysTask.setExecuteStatus(StatusEnum.NO);
      sysTaskService.save(convert(sysTask, SysTaskVo.class));
      int day = param.getInteger("day");
      String time = DateUtil.nextDay(day - 2 * day, DateUtil.FULL_TIME_FORMAT);
      sysTaskLogService.deleteTaskLogByCreateTime(time);

      sysTask.setExecuteStatus(StatusEnum.YES);
      sysTaskService.save(convert(sysTask, SysTaskVo.class));
      return StatusEnum.YES;
    } catch (Exception e) {
      LOGGER.error("update sysTask Exception:", e);
      return StatusEnum.NO;
    }
  }

  public void saveExecuteTaskLog(SysTaskDto sysTask) {
    LOGGER.info("saveExecuteTaskLog :{}", sysTask);

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
