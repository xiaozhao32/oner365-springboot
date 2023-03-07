package com.oner365.queue.service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;

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
  void sendMessage(JSONObject data);
  
  /**
   * 同步路由数据
   */
  void syncRoute();
  
  /**
   * 发送定时任务到各服务
   * 
   * @param data 参数对象
   */
  void pullTask(InvokeParamDto data);
  
  /**
   * 更新任务执行状态
   * 
   * @param data 对象
   */
  void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data);

  /**
   * 保存任务执行日志
   * 
   * @param data 对象
   */
  void saveExecuteTaskLog(SysTaskDto data);

}
