package com.oner365.monitor.controller.task;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.monitor.entity.SysTaskLog;
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
     * @param paramJson 查询参数
     * @return Page<SysTaskLog>
     */
    @PostMapping("/list")
    @ApiOperation("获取列表")
    public Page<SysTaskLog> list(@RequestBody JSONObject paramJson) {
        return taskLogService.pageList(paramJson);
    }

    /**
     * 导出定时任务调度日志列表
     * 
     * @param paramJson 查询参数
     * @return String
     */
    @GetMapping("/export")
    @ApiOperation("导出")
    public String export(@RequestBody JSONObject paramJson) {
        return PublicConstants.SUCCESS;
    }

    /**
     * 根据调度编号获取详细信息
     * 
     * @param id 主键
     * @return SysTaskLog
     */
    @GetMapping("/{id}")
    @ApiOperation("按id查询")
    public SysTaskLog getInfo(@PathVariable String id) {
        return taskLogService.selectTaskLogById(id);
    }

    /**
     * 删除定时任务调度日志
     * 
     * @param ids 主键
     * @return Map<String, Object>
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除任务日志")
    public Map<String, Object> remove(@PathVariable String[] ids) {
        int code = taskLogService.deleteTaskLogByIds(ids);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
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
        return PublicConstants.SUCCESS;
    }
}
