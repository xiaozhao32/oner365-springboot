package com.oner365.monitor.service;

import org.quartz.SchedulerException;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.service.BaseService;

/**
 * 定时任务调度信息信息 服务层
 *
 * @author liutao
 */
public interface ISysTaskService extends BaseService {
  /**
   * 获取quartz调度器的计划任务
   *
   * @param data 查询参数
   * @return 调度任务集合
   */
  PageInfo<SysTaskDto> pageList(QueryCriteriaBean data);

  /**
   * 通过调度任务ID查询调度信息
   *
   * @param id 调度任务ID
   * @return 调度任务对象信息
   */
  SysTaskDto selectTaskById(String id);

  /**
   * 暂停任务
   *
   * @param task 调度信息
   * @return 结果
   * @throws SchedulerException SchedulerException
   * @throws TaskException      TaskException
   */
  int pauseTask(SysTaskVo task) throws SchedulerException, TaskException;

  /**
   * 恢复任务
   *
   * @param task 调度信息
   * @return 结果
   * @throws SchedulerException SchedulerException
   * @throws TaskException      TaskException
   */
  int resumeTask(SysTaskVo task) throws SchedulerException, TaskException;

  /**
   * 删除任务后，所对应的trigger也将被删除
   *
   * @param task 调度信息
   * @return 结果
   * @throws SchedulerException SchedulerException
   */
  int deleteTask(String id) throws SchedulerException;

  /**
   * 批量删除调度信息
   *
   * @param ids 需要删除的任务ID
   * @throws SchedulerException SchedulerException
   */
  void deleteTaskByIds(String[] ids) throws SchedulerException;

  /**
   * 任务调度状态修改
   *
   * @param task 调度信息
   * @return 结果
   * @throws SchedulerException SchedulerException
   * @throws TaskException      TaskException
   */
  int changeStatus(SysTaskVo task) throws SchedulerException, TaskException;

  /**
   * 立即运行任务
   *
   * @param task 调度信息
   * @throws SchedulerException SchedulerException
   */
  void run(SysTaskVo task) throws SchedulerException;

  /**
   * 保存任务
   *
   * @param task 调度信息
   * @return 结果
   * @throws SchedulerException SchedulerException
   * @throws TaskException      TaskException
   */
  int save(SysTaskVo task) throws SchedulerException, TaskException;

  /**
   * 更新任务
   *
   * @param task 调度信息
   * @return 结果
   * @throws SchedulerException SchedulerException
   * @throws TaskException      TaskException
   */
  int updateTask(SysTaskVo task) throws SchedulerException, TaskException;

  /**
   * 校验cron表达式是否有效
   *
   * @param cronExpression 表达式
   * @return 结果
   */
  boolean checkCronExpressionIsValid(String cronExpression);

}
