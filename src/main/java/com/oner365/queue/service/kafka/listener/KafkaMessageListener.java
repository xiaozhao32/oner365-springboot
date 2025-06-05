package com.oner365.queue.service.kafka.listener;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.oner365.queue.condition.KafkaCondition;
import com.oner365.queue.constants.QueueConstants;

/**
 * Kafka 监听服务
 *
 * @author zhaoyong
 */
@Component
@Conditional(KafkaCondition.class)
public class KafkaMessageListener {

    private final Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    /**
     * 监听服务
     * @param consumerRecord 参数
     */
    @KafkaListener(id = QueueConstants.MESSAGE_QUEUE_NAME, topics = { QueueConstants.MESSAGE_QUEUE_NAME })
    public void listener(ConsumerRecord<String, ?> consumerRecord) {
        Optional<?> kafkaMessage = Optional.of(consumerRecord.value());
        Object message = kafkaMessage.get();
        logger.info("Kafka Message received: {}", message);
    }

}
