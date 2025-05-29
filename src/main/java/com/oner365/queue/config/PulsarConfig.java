package com.oner365.queue.config;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.enums.QueueEnum;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;

/**
 * pulsar config
 *
 * @author zhaoyong
 *
 */
@Configuration
@Conditional(PulsarCondition.class)
@EnableConfigurationProperties({ PulsarProperties.class })
public class PulsarConfig {

    private final Logger logger = LoggerFactory.getLogger(PulsarConfig.class);

    @Resource
    private PulsarProperties pulsarProperties;

    public PulsarConfig() {
        logger.info("Queue Type: {}", QueueEnum.PULSAR);
    }

    @Bean
    PulsarClient getPulsarFactory() {
        PulsarClient client = null;
        try {
            client = PulsarClient.builder().serviceUrl("pulsar://" + pulsarProperties.getUrl()).build();
        }
        catch (PulsarClientException e) {
            logger.error("PulsarClient factory error:", e);
        }
        return client;
    }

    @PreDestroy
    public void destroy() {
        try {
            logger.info("Destroy Pulsar factory.");
            getPulsarFactory().close();
        }
        catch (PulsarClientException e) {
            logger.error("Destroy Pulsar error:", e);
        }
    }

}
