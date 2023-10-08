package com.oner365.queue.service.kafka.listener;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.queue.condition.KafkaCondition;
import com.oner365.queue.constants.QueueConstants;

/**
 * Kafka 监听服务
 *
 * @author zhaoyong
 */
@Component
@Conditional(KafkaCondition.class)
public class KafkaInvokeParamListener {

  private final Logger logger = LoggerFactory.getLogger(KafkaInvokeParamListener.class);
  
  @Resource
  private ISysTaskLogService sysTaskLogService;

  @Resource
  private ISysTaskService sysTaskService;

  /**
   * 监听服务
   *
   * @param record 参数
   */
  @KafkaListener(id = QueueConstants.SCHEDULE_TASK_QUEUE_NAME, topics = { QueueConstants.SCHEDULE_TASK_QUEUE_NAME })
  public void listener(ConsumerRecord<String, ?> record) {
    Optional<?> kafkaMessage = Optional.of(record.value());
    if (kafkaMessage.isPresent()) {
      Object message = kafkaMessage.get();
      logger.info("Kafka pullTask received: {}", message);
      
      // bussiness
    }
  }
  
}
