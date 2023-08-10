package com.oner365.queue.service.pulsar.listener;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;
import com.oner365.util.DataUtils;

/**
 * pulsar SysTaskDto listener
 * 
 * @author zhaoyong
 *
 */
@Component
@Conditional(PulsarCondition.class)
public class PulsarTaskLogListener implements MessageListener<SysTaskDto> {

  private static final long serialVersionUID = 1L;

  private final Logger logger = LoggerFactory.getLogger(PulsarTaskLogListener.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Resource
  private ISysTaskLogService sysTaskLogService;

  @Override
  public void received(Consumer<SysTaskDto> consumer, Message<SysTaskDto> msg) {
    try {
      String data = String.valueOf(msg.getData());
      logger.info("Pulsar consumer data: {}, topic: {}", data, consumer.getTopic());
      consumer.acknowledge(msg);
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
    // business
    SysTaskDto sysTask = msg.getValue();
    if (sysTask != null) {
      saveExecuteTaskLog(sysTask);
    }

  }

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
