package com.oner365.queue.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.enums.QueueEnum;
import com.oner365.queue.condition.KafkaCondition;
import com.oner365.queue.config.properties.KafkaProperties;

/**
 * Kafka config
 *
 * @author zhaoyong
 *
 */
@Configuration
@Conditional(KafkaCondition.class)
@EnableConfigurationProperties({ KafkaProperties.class })
public class KafkaConfig {

  public KafkaConfig() {
    Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
    logger.info("Queue Type: {}", QueueEnum.KAFKA);
  }

}
