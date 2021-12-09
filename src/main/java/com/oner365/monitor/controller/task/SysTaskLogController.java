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
@RequestMapping("/monitor/taskLog")
@Api(tags = "监控 - 定时任务日志")
public class SysTaskLogController extends BaseController {
    
    @Autowired
    private ISysTaskLogService taskLogService;

    /**
     * 查询定时任务调度日志列表
     * 
     * @param data 查询参数
     * @return Page<SysTaskLogDto>
     */
    @PostMapping("/list")
    @ApiOperation("获取列表")
    public Page<SysTaskLogDto> list(@RequestBody QueryCriteriaBean data) {
        return taskLogService.pageList(data);
    }

    /**
     * 导出定时任务调度日志列表
     * 
     * @param data 查询参数
     * @return String
     */
    @GetMapping("/export")
    @ApiOperation("导出")
    public String export(@RequestBody QueryCriteriaBean data) {
        return ResultEnum.SUCCESS.getName();
    }

    /**
     * 根据调度编号获取详细信息
     * 
     * @param id 主键
     * @return SysTaskLogDto
     */
    @GetMapping("/{id}")
    @ApiOperation("按id查询")
    public SysTaskLogDto getInfo(@PathVariable String id) {
        return taskLogService.selectTaskLogById(id);
    }

    /**
     * 删除定时任务调度日志
     * 
     * @param ids 主键
     * @return Integer
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除任务日志")
    public Integer remove(@PathVariable String[] ids) {
        return taskLogService.deleteTaskLogByIds(ids);
    }

    /**
     * 清空定时任务调度日志
     * 
     * @return String
     */
    @DeleteMapping("/clean")
    @ApiOperation("清除任务日志")
    public String clean() {
        taskLogService.cleanTaskLog();
        return ResultEnum.SUCCESS.getName();
    }
}
