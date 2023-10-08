package com.oner365.queue.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.common.enums.QueueEnum;
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

  private final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
  
  public KafkaConfig() {
    logger.info("Queue Type: {}", QueueEnum.KAFKA);
  }

}
