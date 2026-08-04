package com.oner365.queue.service.pulsar.listener;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

import com.oner365.data.commons.util.GsonUtils;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.constants.QueueConstants;

import jakarta.annotation.Resource;

/**
 * pulsar SysTaskDto listener
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(PulsarCondition.class)
public class PulsarTaskLogListenerImpl implements BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PulsarTaskLogListenerImpl.class);

    @Resource
    private ISysTaskLogService sysTaskLogService; // NOSONAR

    @PulsarListener(topics = QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, subscriptionName = "saveExecuteTaskLog")
    public void listener(String data) {
        LOGGER.info("Pulsar consumer data: {}, topic: {}", data, QueueConstants.SAVE_TASK_LOG_QUEUE_NAME);
        // business
        SysTaskDto sysTask = GsonUtils.jsonToBean(data, SysTaskDto.class);
        if (sysTask != null) {
            saveExecuteTaskLog(sysTask);
        }

    }

    public void saveExecuteTaskLog(SysTaskDto sysTask) {
        LOGGER.info("saveExecuteTaskLog :{}", sysTask);

        long time = System.currentTimeMillis();
        SysTaskLogVo taskLog = new SysTaskLogVo();
        taskLog.setStartTime(LocalDateTime.now());
        taskLog.setExecuteIp(HttpClientUtils.getLocalhost());
        taskLog.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
        taskLog.setStatus(TaskStatusEnum.NORMAL);
        taskLog.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
        taskLog.setTaskGroup(sysTask.getTaskGroup());
        taskLog.setTaskName(sysTask.getTaskName());
        taskLog.setInvokeTarget(sysTask.getInvokeTarget());
        taskLog.setCreateUser(sysTask.getCreateUser());
        taskLog.setStopTime(LocalDateTime.now());
        sysTaskLogService.addTaskLog(taskLog);
    }

}
