package com.oner365.queue.service.rabbitmq;

import java.io.IOException;

import org.quartz.SchedulerException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Conditional;

import com.oner365.data.jpa.service.BaseService;
import com.oner365.monitor.exception.TaskException;
import com.oner365.queue.condition.RabbitmqCondition;
import com.oner365.queue.constants.QueueConstants;
import com.rabbitmq.client.Channel;

/**
 * rabbitmq 接收队列
 *
 * @author zhaoyong
 *
 */
@Conditional(RabbitmqCondition.class)
public interface IQueueRabbitmqReceiverService extends BaseService {

    /**
     * 消息接口
     * @param msg 消息内容
     * @param channel channel
     * @param message 消息对象
     * @throws IOException 异常信息
     */
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = QueueConstants.MESSAGE_QUEUE_NAME, autoDelete = "false"),
                    exchange = @Exchange(value = QueueConstants.MESSAGE_QUEUE_TYPE),
                    key = QueueConstants.MESSAGE_QUEUE_KEY),
            ackMode = "MANUAL")
    void message(String msg, Channel channel, Message message) throws IOException;

    /**
     * 同步路由数据
     * @param gatewayIp 网关ip
     */
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = QueueConstants.ROUTE_QUEUE_NAME, autoDelete = "false"),
                    exchange = @Exchange(value = QueueConstants.ROUTE_QUEUE_TYPE, type = ExchangeTypes.FANOUT),
                    key = QueueConstants.ROUTE_QUEUE_KEY))
    void syncRoute(String gatewayIp);

    /**
     * 定时任务监听
     * @param data 参数对象
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConstants.SCHEDULE_TASK_QUEUE_NAME, autoDelete = "false"),
            exchange = @Exchange(value = QueueConstants.SCHEDULE_TASK_QUEUE_TYPE, type = ExchangeTypes.FANOUT),
            key = QueueConstants.SCHEDULE_TASK_QUEUE_KEY))
    void scheduleTask(String data);

    /**
     * 更新任务执行状态
     * @param data 任务对象
     * @throws SchedulerException SchedulerException
     * @throws TaskException TaskException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, autoDelete = "false"),
            exchange = @Exchange(value = QueueConstants.TASK_UPDATE_STATUS_QUEUE_TYPE, type = ExchangeTypes.FANOUT),
            key = QueueConstants.TASK_UPDATE_STATUS_QUEUE_KEY))
    void updateTaskExecuteStatus(String data) throws SchedulerException, TaskException;

    /**
     * 保存任务执行日志
     * @param data 任务对象
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, autoDelete = "false"),
            exchange = @Exchange(value = QueueConstants.SAVE_TASK_LOG_QUEUE_TYPE, type = ExchangeTypes.FANOUT),
            key = QueueConstants.SAVE_TASK_LOG_QUEUE_KEY))
    void saveExecuteTaskLog(String data);

}
