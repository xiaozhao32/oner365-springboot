package com.oner365.queue.service.pulsar.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.constants.QueueConstants;

/**
 * pulsar message listener
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(PulsarCondition.class)
public class PulsarMessageListenerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PulsarMessageListenerImpl.class);

    @PulsarListener(topics = QueueConstants.MESSAGE_QUEUE_NAME, subscriptionName = "sendMessage")
    public void listener(String data) {
        LOGGER.info("Pulsar consumer data: {}, topic: {}", data, QueueConstants.MESSAGE_QUEUE_NAME);
    }

}
