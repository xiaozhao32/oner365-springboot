package com.oner365.queue.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.common.enums.QueueEnum;
import com.oner365.queue.condition.RabbitmqCondition;
import com.oner365.queue.config.properties.RabbitmqProperties;

/**
 * Rabbit admin config
 * 
 * @author zhaoyong
 *
 */
@Configuration
@Conditional(RabbitmqCondition.class)
@EnableConfigurationProperties({ RabbitmqProperties.class })
public class RabbitAdminConfig {
  
  private final Logger logger = LoggerFactory.getLogger(RabbitAdminConfig.class);

  @Resource
  private ConnectionFactory connectionFactory;
  
  public RabbitAdminConfig() {
    logger.info("Queue Type: {}", QueueEnum.RABBITMQ);
  }

  @Bean
  RabbitAdmin rabbitAdmin() {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    rabbitAdmin.setAutoStartup(true);
    return rabbitAdmin;
  }
  
}
