package com.oner365.queue.service.pulsar.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.constants.QueueConstants;

import jakarta.annotation.Resource;

/**
 * pulsar Route listener
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(PulsarCondition.class)
public class PulsarRouteListenerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PulsarRouteListenerImpl.class);

    @Resource
    private DynamicRouteService dynamicRouteService; // NOSONAR

    @PulsarListener(topics = QueueConstants.ROUTE_QUEUE_NAME, subscriptionName = "syncRoute")
    public void listener(String data) {
        LOGGER.info("Pulsar consumer data: {}, topic: {}", data, QueueConstants.ROUTE_QUEUE_NAME);

        // business
        dynamicRouteService.refreshRoute();

    }

}
