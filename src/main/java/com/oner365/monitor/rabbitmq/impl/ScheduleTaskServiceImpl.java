package com.oner365.monitor.rabbitmq.impl;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.rabbitmq.IScheduleTaskService;
import com.oner365.api.rabbitmq.dto.InvokeParamDto;
import com.oner365.common.constants.PublicConstants;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.entity.SysTask;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ScheduleTask实现类
 *
 * @author zhaoyong
 */
@Service
public class ScheduleTaskServiceImpl implements IScheduleTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTaskServiceImpl.class);

    @Autowired
    private ISysTaskLogService sysTaskLogService;

    @Autowired
    private ISysTaskService sysTaskService;

    @Override
    public void scheduleTask(InvokeParamDto invokeParamDto) {
        if (ScheduleConstants.SCHEDULE_SERVER_NAME.equals(invokeParamDto.getTaskServerName())) {
            taskExecute(invokeParamDto.getConcurrent(), invokeParamDto.getTaskId(), invokeParamDto.getTaskParam());
        }
    }

    private void taskExecute(String concurrent, String taskId, JSONObject param) {
        String status = PublicConstants.STATUS_YES;
        SysTask sysTask = sysTaskService.selectTaskById(taskId);
        if (sysTask != null) {
            if (ScheduleConstants.SCHEDULE_CONCURRENT.equals(concurrent)) {
                LOGGER.info("taskExecute  concurrent : {} , update sysTask  executeStatus = 0", concurrent);
                status = execute(taskId, param, sysTask);

            } else {
                if (!PublicConstants.STATUS_NO.equals(sysTask.getExecuteStatus())) {
                    sysTask.getInvokeParam();
                    status = execute(taskId, param, sysTask);
                }
                LOGGER.info("taskExecute  concurrent : {}", concurrent);
            }
            executeLog(sysTask, status);
        }
    }

    private String execute(String taskId, JSONObject param, SysTask sysTask) {
        try {
            LOGGER.info("taskId:{}", taskId);
            sysTask.setExecuteStatus(PublicConstants.STATUS_NO);
            sysTaskService.save(sysTask);
            int day = param.getInteger("day");
            String time = DateUtil.nextDay(day - 2 * day, DateUtil.FULL_TIME_FORMAT);
            String status = sysTaskLogService.deleteTaskLogByCreateTime(time);
            sysTask.setExecuteStatus(PublicConstants.STATUS_YES);
            sysTaskService.save(sysTask);
            return status;
        } catch (Exception e) {
            LOGGER.error("update sysTask Exception:", e);
            return PublicConstants.STATUS_NO;
        }

    }

    private void executeLog(SysTask sysTask, String status) {
        LOGGER.info("saveTaskLog status:{}", status);
        long time = System.currentTimeMillis();
        SysTaskLog log = new SysTaskLog();
        log.setExecuteIp(DataUtils.getLocalhost());
        log.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
        log.setStatus(PublicConstants.STATUS_YES);
        log.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
        log.setTaskGroup(sysTask.getTaskGroup());
        log.setTaskName(sysTask.getTaskName());
        log.setInvokeTarget(sysTask.getInvokeTarget());
        sysTaskLogService.addTaskLog(log);
    }

}
