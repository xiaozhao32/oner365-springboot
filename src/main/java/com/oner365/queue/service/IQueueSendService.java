package com.oner365.queue.service;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskLogDto;

/**
 * 发送队列
 * 
 * @author zhaoyong
 *
 */
public interface IQueueSendService {
  
  /**
   * 发送消息
   * 
   * @param data 消息内容
   */
  void sendMessage(String data);
  
  /**
   * 同步路由数据
   */
  void syncRoute();
  
  /**
   * 发送定时任务到各服务
   * 
   * @param invokeParamDto 参数对象
   */
  void pullTask(InvokeParamDto invokeParamDto);
  
  /**
   * 更新任务执行状态
   * 
   * @param updateTaskExecuteStatusDto 对象
   */
  void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto updateTaskExecuteStatusDto);

  /**
   * 保存任务执行日志
   * 
   * @param sysTaskLogDto 对象
   */
  void saveExecuteTaskLog(SysTaskLogDto sysTaskLogDto);

}
