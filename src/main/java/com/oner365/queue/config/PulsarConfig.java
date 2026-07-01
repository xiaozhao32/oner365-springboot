package com.oner365.queue.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.enums.QueueEnum;
import com.oner365.queue.condition.PulsarCondition;

/**
 * pulsar config
 *
 * @author zhaoyong
 *
 */
@Configuration
@Conditional(PulsarCondition.class)
public class PulsarConfig {

    private final Logger logger = LoggerFactory.getLogger(PulsarConfig.class);

    public PulsarConfig() {
        logger.info("Queue Type: {}", QueueEnum.PULSAR);
    }

}
