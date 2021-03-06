package com.oner365.queue.service;

import org.quartz.SchedulerException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.common.service.BaseService;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.exception.TaskException;
import com.oner365.queue.constants.RabbitmqConstants;

/**
 * rabbitmq 接收队列
 * 
 * @author zhaoyong
 *
 */
public interface IQueueRabbitmqReceiverService extends BaseService {

  /**
   * 消息接口
   *
   * @param message 消息
   */
  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = RabbitmqConstants.MESSAGE_QUEUE_NAME, autoDelete = "false"),
          exchange = @Exchange(value = RabbitmqConstants.MESSAGE_QUEUE_TYPE),
          key = RabbitmqConstants.MESSAGE_QUEUE_KEY
      )
  )
  void message(String message);
  
  /**
   * 同步路由数据
   * @param gatewayIp 网关ip
   */
  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = RabbitmqConstants.ROUTE_QUEUE_NAME, autoDelete = "false"),
          exchange = @Exchange(value = RabbitmqConstants.ROUTE_QUEUE_TYPE, type = ExchangeTypes.FANOUT),
          key = RabbitmqConstants.ROUTE_QUEUE_KEY
      )
  )
  void syncRoute(String gatewayIp);
  
  /**
   * 定时任务监听
   * 
   * @param invokeParamDto 参数对象
   */
  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = RabbitmqConstants.SCHEDULE_TASK_QUEUE_NAME, autoDelete = "false"), 
          exchange = @Exchange(value = RabbitmqConstants.SCHEDULE_TASK_QUEUE_TYPE, type = ExchangeTypes.FANOUT), 
          key = RabbitmqConstants.SCHEDULE_TASK_QUEUE_KEY)
  )
  void scheduleTask(InvokeParamDto invokeParamDto);
  
  /**
   * 更新任务执行状态
   * 
   * @param updateTask 任务对象
   * @throws SchedulerException SchedulerException
   * @throws TaskException      TaskException
   */
  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_NAME, autoDelete = "false"), 
          exchange = @Exchange(value = RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_TYPE, type = ExchangeTypes.FANOUT), 
          key = RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_KEY
      )
  )
  void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto updateTask) throws SchedulerException, TaskException;

  /**
   * 保存任务执行日志
   * 
   * @param sysTask 任务对象
   */
  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = RabbitmqConstants.SAVE_TASK_LOG_QUEUE_NAME, autoDelete = "false"), 
          exchange = @Exchange(value = RabbitmqConstants.SAVE_TASK_LOG_QUEUE_TYPE, type = ExchangeTypes.FANOUT), 
          key = RabbitmqConstants.SAVE_TASK_LOG_QUEUE_KEY
      )
  )
  void saveExecuteTaskLog(SysTaskDto sysTask);
}
