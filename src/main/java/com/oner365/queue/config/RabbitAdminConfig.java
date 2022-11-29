package com.oner365.queue.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * Rabbit admin config
 * 
 * @author zhaoyong
 *
 */
@Component
public class RabbitAdminConfig {

  @Resource
  private ConnectionFactory connectionFactory;

  @Bean
  public RabbitAdmin rabbitAdmin() {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    rabbitAdmin.setAutoStartup(true);
    return rabbitAdmin;
  }

}
