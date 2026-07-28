package com.oner365.test.controller.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.data.commons.config.properties.DefaultQueueProperties;
import com.oner365.data.commons.enums.QueueEnum;
import com.oner365.test.controller.BaseControllerTest;

import jakarta.annotation.Resource;

/**
 * Test RabbitmqController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RabbitmqControllerTest extends BaseControllerTest {

    private static final String PATH = "/monitor/rabbitmq";
    
    @Resource
    private DefaultQueueProperties defaultQueueProperties;

    @Test
    void index() {
        QueueEnum queueType = defaultQueueProperties.getType();
        logger.info("Queue Type: {}", queueType);
        Assertions.assertNotNull(queueType);
        
        // Rabbitmq Test
        if (QueueEnum.RABBITMQ.equals(queueType)) {
            String url = PATH + "/index";
            Object result = get(url);
            logger.info("index:[{}] -> {}", url, result);
            Assertions.assertNotNull(result);
        }
    }

    @Test
    void list() {
        QueueEnum queueType = defaultQueueProperties.getType();
        logger.info("Queue Type: {}", queueType);
        Assertions.assertNotNull(queueType);
        
        // Rabbitmq Test
        if (QueueEnum.RABBITMQ.equals(queueType)) {
            String url = PATH + "/list/EXCHANGES?pageIndex=1&pageSize=5";
            Object result = get(url);
            logger.info("list:[{}] -> {}", url, result);
            Assertions.assertNotNull(result);
        }
    }

}
