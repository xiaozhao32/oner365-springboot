package com.oner365.init;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.oner365.queue.condition.RabbitmqCondition;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.sys.constants.SysMessageConstants;

/**
 * Rabbitmq Admin
 *
 * @author zhaoyong
 */
@Component
@Conditional(RabbitmqCondition.class)
public class RabbitmqRunner implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(RabbitmqRunner.class);

    @Resource
    private RabbitAdmin rabbitAdmin;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // rabbitmq init config
    }

    @PreDestroy
    public void destroy() {
        rabbitAdmin.deleteQueue(QueueConstants.MESSAGE_QUEUE_NAME);
        rabbitAdmin.deleteQueue(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME);
        rabbitAdmin.deleteQueue(QueueConstants.SCHEDULE_TASK_QUEUE_NAME);
        rabbitAdmin.deleteQueue(QueueConstants.ROUTE_QUEUE_NAME);
        rabbitAdmin.deleteQueue(SysMessageConstants.QUEUE_NAME);
        rabbitAdmin.deleteQueue(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME);
        logger.info("Destroy Rabbitmq queue.");

        rabbitAdmin.deleteExchange(QueueConstants.MESSAGE_QUEUE_TYPE);
        rabbitAdmin.deleteExchange(QueueConstants.SAVE_TASK_LOG_QUEUE_TYPE);
        rabbitAdmin.deleteExchange(QueueConstants.SCHEDULE_TASK_QUEUE_TYPE);
        rabbitAdmin.deleteExchange(QueueConstants.ROUTE_QUEUE_TYPE);
        rabbitAdmin.deleteExchange(SysMessageConstants.QUEUE_TYPE);
        rabbitAdmin.deleteExchange(QueueConstants.TASK_UPDATE_STATUS_QUEUE_TYPE);
        logger.info("Destroy Rabbitmq exchange.");
    }

}
