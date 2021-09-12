package com.oner365.monitor.controller.task;

import java.util.Map;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.monitor.entity.SysTask;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.util.CronUtils;
import com.oner365.sys.vo.SysTaskVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 调度任务信息操作处理
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/monitor/task")
@Api(tags = "监控 - 定时任务")
public class SysTaskController extends BaseController {

    @Autowired
    private ISysTaskService taskService;

    /**
     * 查询定时任务列表
     *
     * @param paramJson 查询参数
     * @return Page<SysTask>
     */
    @PostMapping("/list")
    @ApiOperation("定时任务列表")
    public Page<SysTask> list(@RequestBody JSONObject paramJson) {
        return taskService.pageList(paramJson);
    }

    /**
     * 导出定时任务列表
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
     * 获取定时任务详细信息
     *
     * @param id 主键
     * @return SysTask
     */
    @GetMapping("/{id}")
    @ApiOperation("按id查询信息")
    public SysTask getInfo(@PathVariable String id) {
        return taskService.selectTaskById(id);
    }

    /**
     * 新增定时任务
     *
     * @param sysTaskVo 参数
     * @param authUser 登录对象
     * @return Map<String, Object>
     * @throws SchedulerException, TaskException 异常
     */
    @PostMapping
    @ApiOperation("新增定时任务")
    public Map<String, Object> add(@RequestBody SysTaskVo sysTaskVo, @CurrentUser AuthUser authUser)
            throws SchedulerException, TaskException {
        SysTask sysTask = sysTaskVo.toObject();

        Map<String, Object> result = Maps.newHashMap();
        if (sysTask == null || !CronUtils.isValid(sysTask.getCronExpression())) {
            result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
            result.put(PublicConstants.MSG, "cron表达式不正确");
            return result;
        }
        sysTask.setCreateUser(authUser.getUserName());

        int code = taskService.save(sysTask);
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 修改定时任务
     *
     * @param sysTaskVo 参数
     * @param authUser 登录对象
     * @return Map<String, Object>
     * @throws SchedulerException, TaskException 异常
     */
    @PutMapping
    @ApiOperation("修改定时任务")
    public Map<String, Object> edit(@RequestBody SysTaskVo sysTaskVo, @CurrentUser AuthUser authUser)
            throws SchedulerException, TaskException {
        SysTask sysTask = sysTaskVo.toObject();

        Map<String, Object> result = Maps.newHashMap();
        if (sysTask == null || !CronUtils.isValid(sysTask.getCronExpression())) {
            result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
            result.put(PublicConstants.MSG, "cron表达式不正确");
            return result;
        }
        int code = taskService.updateTask(sysTask);
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 定时任务状态修改
     *
     * @param sysTaskVo 参数
     * @return Map<String, Object>
     * @throws SchedulerException, TaskException 异常
     */
    @PutMapping("/changeStatus")
    @ApiOperation("修改状态")
    public Map<String, Object> changeStatus(@RequestBody SysTaskVo sysTaskVo) throws SchedulerException, TaskException {
        SysTask sysTask = sysTaskVo.toObject();

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysTask != null) {
            SysTask newJob = taskService.selectTaskById(sysTask.getId());
            newJob.setStatus(sysTask.getStatus());
            int code = taskService.changeStatus(newJob);
            result.put(PublicConstants.CODE, code);
        }
        return result;
    }

    /**
     * 定时任务立即执行一次
     *
     * @param sysTaskVo 参数
     * @return Map<String, Object>
     * @throws SchedulerException, TaskException 异常
     */
    @PutMapping("/run")
    @ApiOperation("立即执行一次")
    public Map<String, Object> run(@RequestBody SysTaskVo sysTaskVo) throws SchedulerException {
        SysTask sysTask = sysTaskVo.toObject();
        if (sysTask != null) {
            taskService.run(sysTask);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
        return result;
    }

    /**
     * 删除定时任务
     *
     * @param ids 主键
     * @return Map<String, Object>
     * @throws SchedulerException, TaskException 异常
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除定时任务")
    public Map<String, Object> remove(@PathVariable String[] ids) throws SchedulerException {
        taskService.deleteTaskByIds(ids);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
        return result;
    }
}
