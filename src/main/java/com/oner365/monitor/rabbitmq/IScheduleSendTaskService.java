package com.oner365.monitor.rabbitmq;

import com.oner365.monitor.dto.InvokeParamDto;

/**
 * 定时任务队列
 * 
 * @author liutao
 *
 */
public interface IScheduleSendTaskService {

  /**
   * 发送定时任务到各服务
   * 
   * @param invokeParamDto 参数对象
   */
  void pullTask(InvokeParamDto invokeParamDto);
}
