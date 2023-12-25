package com.oner365.queue.service.kafka.listener;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.KafkaCondition;
import com.oner365.queue.constants.QueueConstants;

import jakarta.annotation.Resource;

/**
 * Kafka 监听服务
 *
 * @author zhaoyong
 */
@Component
@Conditional(KafkaCondition.class)
public class KafkaTaskExecuteStatusListener {

  private final Logger logger = LoggerFactory.getLogger(KafkaTaskExecuteStatusListener.class);

  @Resource
  private ISysTaskService sysTaskService;

  /**
   * 监听服务
   *
   * @param record 参数
   */
  @KafkaListener(id = QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, topics = { QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME })
  public void listener(ConsumerRecord<String, ?> record) {
    Optional<?> kafkaMessage = Optional.of(record.value());
    Object message = kafkaMessage.get();
    logger.info("Kafka updateTaskExecuteStatus received: {}", message);

    // business
    UpdateTaskExecuteStatusDto updateTask = JSON.parseObject(message.toString(), UpdateTaskExecuteStatusDto.class);
    if (updateTask != null) {
      SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
      if (sysTask != null) {
        sysTask.setExecuteStatus(updateTask.getExecuteStatus());
        try {
          sysTaskService.save(convert(sysTask));
        } catch (Exception e) {
          logger.error("save error", e);
        }
      }
    }
  }

  SysTaskVo convert(SysTaskDto source) {
    if (source == null) {
      return null;
    }
    return JSON.parseObject(JSON.toJSONString(source), SysTaskVo.class);
  }
}
