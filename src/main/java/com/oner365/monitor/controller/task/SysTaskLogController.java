package com.oner365.monitor.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.monitor.dto.SysTaskLogDto;
import com.oner365.monitor.service.ISysTaskLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 调度日志操作处理
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "监控 - 定时任务日志")
@RequestMapping("/monitor/taskLog")
public class SysTaskLogController extends BaseController {

  @Autowired
  private ISysTaskLogService taskLogService;

  /**
   * 查询定时任务调度日志列表
   * 
   * @param data 查询参数
   * @return Page<SysTaskLogDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public Page<SysTaskLogDto> list(@RequestBody QueryCriteriaBean data) {
    return taskLogService.pageList(data);
  }

  /**
   * 根据调度编号获取详细信息
   * 
   * @param id 主键
   * @return SysTaskLogDto
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/{id}")
  public SysTaskLogDto getInfo(@PathVariable String id) {
    return taskLogService.selectTaskLogById(id);
  }

  /**
   * 清空定时任务调度日志
   * 
   * @return String
   */
  @ApiOperation("3.清除任务日志")
  @ApiOperationSupport(order = 3)
  @DeleteMapping("/clean")
  public String clean() {
    taskLogService.cleanTaskLog();
    return ResultEnum.SUCCESS.getName();
  }

  /**
   * 删除定时任务调度日志
   * 
   * @param ids 主键
   * @return Integer
   */
  @ApiOperation("4.删除任务日志")
  @ApiOperationSupport(order = 4)
  @DeleteMapping("/{ids}")
  public Integer remove(@PathVariable String[] ids) {
    return taskLogService.deleteTaskLogByIds(ids);
  }

  /**
   * 导出定时任务调度日志列表
   * 
   * @param data 查询参数
   * @return String
   */
  @ApiOperation("5.导出")
  @ApiOperationSupport(order = 5)
  @GetMapping("/export")
  public String export(@RequestBody QueryCriteriaBean data) {
    return ResultEnum.SUCCESS.getName();
  }

}
