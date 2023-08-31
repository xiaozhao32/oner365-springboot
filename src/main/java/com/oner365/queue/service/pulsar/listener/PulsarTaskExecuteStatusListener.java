package com.oner365.queue.service.pulsar.listener;

import jakarta.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.common.service.BaseService;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;

/**
 * pulsar UpdateTaskExecuteStatusDto listener
 * 
 * @author zhaoyong
 *
 */
@Component
@Conditional(PulsarCondition.class)
public class PulsarTaskExecuteStatusListener implements MessageListener<UpdateTaskExecuteStatusDto>, BaseService {

  private static final long serialVersionUID = 1L;

  private final Logger logger = LoggerFactory.getLogger(PulsarTaskExecuteStatusListener.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Resource
  private ISysTaskService sysTaskService;

  @Override
  public void received(Consumer<UpdateTaskExecuteStatusDto> consumer, Message<UpdateTaskExecuteStatusDto> msg) {
    try {
      String data = String.valueOf(msg.getData());
      logger.info("Pulsar consumer data: {}, topic: {}", data, consumer.getTopic());
      consumer.acknowledge(msg);
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
    // business
    UpdateTaskExecuteStatusDto updateTask = msg.getValue();
    if (updateTask != null) {
      SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
      if (sysTask != null) {
        sysTask.setExecuteStatus(updateTask.getExecuteStatus());
        try {
          sysTaskService.save(convert(sysTask, SysTaskVo.class));
        } catch (Exception e) {
          logger.error("save error", e);
        }
      }
    }

  }

}
