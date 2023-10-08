package com.oner365.queue.service.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.oner365.queue.condition.KafkaCondition;
import com.oner365.queue.constants.QueueConstants;

/**
 * Kafka 监听服务
 *
 * @author zhaoyong
 */
@Component
@Conditional(KafkaCondition.class)
public class KafkaTaskExecuteStatusListener {

  private final Logger logger = LoggerFactory.getLogger(KafkaTaskExecuteStatusListener.class);

  /**
   * 监听服务
   *
   * @param record 参数
   */
  @KafkaListener(id = QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, topics = { QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME })
  public void listener(ConsumerRecord<String, ?> record) {
    Optional<?> kafkaMessage = Optional.of(record.value());
    if (kafkaMessage.isPresent()) {
      Object message = kafkaMessage.get();
      logger.info("Kafka updateTaskExecuteStatus received: {}", message);
      
      // business
    }
  }
}
