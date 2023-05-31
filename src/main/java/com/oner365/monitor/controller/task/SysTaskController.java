package com.oner365.monitor.controller.task;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.util.CronUtils;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 调度任务信息操作处理
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "监控 - 定时任务")
@RequestMapping("/monitor/task")
public class SysTaskController extends BaseController {

  @Resource
  private ISysTaskService taskService;

  /**
   * 查询定时任务列表
   *
   * @param data 查询参数
   * @return PageInfo<SysTaskDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysTaskDto> pageList(@RequestBody QueryCriteriaBean data) {
    return taskService.pageList(data);
  }

  /**
   * 获取定时任务详细信息
   *
   * @param id 主键
   * @return SysTask
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/{id}")
  public SysTaskDto getInfo(@PathVariable String id) {
    return taskService.selectTaskById(id);
  }

  /**
   * 新增定时任务
   *
   * @param sysTaskVo 参数
   * @param authUser  登录对象
   * @return ResponseResult<Boolean>
   * @throws SchedulerException, TaskException 异常
   */
  @ApiOperation("3.新增定时任务")
  @ApiOperationSupport(order = 3)
  @PostMapping
  public ResponseResult<Boolean> add(@Validated @RequestBody SysTaskVo sysTaskVo, @ApiIgnore @CurrentUser AuthUser authUser)
      throws SchedulerException, TaskException {
    if (sysTaskVo == null || !CronUtils.isValid(sysTaskVo.getCronExpression())) {
      return ResponseResult.error("cron表达式不正确");
    }
    sysTaskVo.setCreateUser(authUser.getUserName());

    Boolean result = taskService.save(sysTaskVo);
    return ResponseResult.success(result);
  }

  /**
   * 修改定时任务
   *
   * @param sysTaskVo 参数
   * @param authUser  登录对象
   * @return ResponseResult<Boolean>
   * @throws SchedulerException, TaskException 异常
   */
  @ApiOperation("4.修改定时任务")
  @ApiOperationSupport(order = 4)
  @PutMapping
  public ResponseResult<Boolean> edit(@RequestBody SysTaskVo sysTaskVo, @ApiIgnore @CurrentUser AuthUser authUser)
      throws SchedulerException, TaskException {
    if (sysTaskVo == null || !CronUtils.isValid(sysTaskVo.getCronExpression())) {
      return ResponseResult.error("cron表达式不正确");
    }
    sysTaskVo.setCreateUser(authUser.getUserName());
    sysTaskVo.setUpdateTime(DateUtil.getDate());
    Boolean result = taskService.updateTask(sysTaskVo);
    return ResponseResult.success(result);
  }

  /**
   * 定时任务状态修改
   *
   * @param sysTaskVo 参数
   * @return ResponseResult<Boolean>
   * @throws SchedulerException 异常
   */
  @ApiOperation("5.修改状态")
  @ApiOperationSupport(order = 5)
  @PutMapping("/status")
  public ResponseResult<Boolean> changeStatus(@RequestBody SysTaskVo sysTaskVo)
      throws SchedulerException {
    if (sysTaskVo != null) {
      Boolean result = taskService.changeStatus(sysTaskVo);
      return ResponseResult.success(result);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 定时任务立即执行一次
   *
   * @param sysTaskVo 参数
   * @return ResponseResult<Boolean>
   * @throws SchedulerException 异常
   */
  @ApiOperation("6.立即执行一次")
  @ApiOperationSupport(order = 6)
  @PutMapping("/run")
  public ResponseResult<Boolean> run(@RequestBody SysTaskVo sysTaskVo) throws SchedulerException {
    if (sysTaskVo != null) {
      Boolean result = taskService.run(sysTaskVo);
      return ResponseResult.success(result);
    }
    return ResponseResult.error("执行失败");
  }

  /**
   * 删除定时任务
   *
   * @param ids 主键
   * @return List<Boolean>
   */
  @ApiOperation("7.删除定时任务")
  @ApiOperationSupport(order = 7)
  @DeleteMapping("/delete")
  public List<Boolean> remove(@RequestBody String... ids) {
    return taskService.deleteTaskByIds(ids);
  }

  /**
   * 导出定时任务列表
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("8.导出")
  @ApiOperationSupport(order = 8)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysTaskDto> list = taskService.findList(data);

    String[] titleKeys = new String[] { "编号", "任务名称", "任务组", "调用目标", "目标参数", "执行表达式", "计划策略", 
        "是否并发", "状态", "执行状态", "备注", "创建人", "创建时间", "更新时间" };
    String[] columnNames = { "id", "taskName", "taskGroup", "invokeTarget", "invokeParamDto", "cronExpression", "misfirePolicy", 
        "concurrent", "status", "executeStatus", "remark", "createUser", "createTime", "updateTime" };

    String fileName = SysTaskDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }
}
