package com.oner365.monitor.rabbitmq;

import org.quartz.SchedulerException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.oner365.api.constants.ScheduleTaskConstants;
import com.oner365.api.rabbitmq.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.SysTaskLogDto;
import com.oner365.monitor.exception.TaskException;

/**
 * 定时任务队列
 * 
 * @author liutao
 *
 */
@Component
public interface IScheduleSendExecuteService {

  /**
   * 更新任务执行状态
   * 
   * @param updateTask 任务对象
   * @throws SchedulerException SchedulerException
   * @throws TaskException      TaskException
   */
  @RabbitListener(bindings = @QueueBinding(value = @Queue(value = ScheduleTaskConstants.TASK_UPDATE_STATUS_QUEUE_NAME, autoDelete = "false"), exchange = @Exchange(value = ScheduleTaskConstants.TASK_UPDATE_STATUS_QUEUE_TYPE, type = ExchangeTypes.FANOUT), key = ScheduleTaskConstants.TASK_UPDATE_STATUS_QUEUE_KEY))
  void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto updateTask) throws SchedulerException, TaskException;

  /**
   * 保存任务执行日志
   * 
   * @param taskLog 任务对象
   */
  @RabbitListener(bindings = @QueueBinding(value = @Queue(value = ScheduleTaskConstants.SAVE_TASK_LOG_QUEUE_NAME, autoDelete = "false"), exchange = @Exchange(value = ScheduleTaskConstants.SAVE_TASK_LOG_QUEUE_TYPE, type = ExchangeTypes.FANOUT), key = ScheduleTaskConstants.SAVE_TASK_LOG_QUEUE_KEY))
  void saveExecuteTaskLog(SysTaskLogDto taskLog);
}
