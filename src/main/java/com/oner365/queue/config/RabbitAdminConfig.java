package com.oner365.queue.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.enums.QueueEnum;
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

    @Resource
    private RabbitTemplate rabbitTemplate;

    public RabbitAdminConfig() {
        Logger logger = LoggerFactory.getLogger(RabbitAdminConfig.class);
        logger.info("Queue Type: {}", QueueEnum.RABBITMQ);
    }

    @Bean
    RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

}
