package com.oner365.queue.service.pulsar.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.util.GsonUtils;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.constants.QueueConstants;

import jakarta.annotation.Resource;

/**
 * pulsar UpdateTaskExecuteStatusDto listener
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(PulsarCondition.class)
public class PulsarTaskExecuteStatusListenerImpl implements BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PulsarTaskExecuteStatusListenerImpl.class);

    @Resource
    private ISysTaskService sysTaskService; // NOSONAR

    @PulsarListener(topics = QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, subscriptionName = "updateTaskExecuteStatus")
    public void listener(String data) {
        LOGGER.info("Pulsar consumer data: {}, topic: {}", data, QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME);

        // business
        UpdateTaskExecuteStatusDto updateTask = GsonUtils.jsonToBean(data, UpdateTaskExecuteStatusDto.class);
        if (updateTask != null) {
            SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
            if (sysTask != null) {
                sysTask.setExecuteStatus(updateTask.getExecuteStatus());
                try {
                    sysTaskService.save(convert(sysTask, SysTaskVo.class));
                }
                catch (Exception e) {
                    LOGGER.error("save error", e);
                }
            }
        }

    }

}
