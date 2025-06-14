package com.oner365.queue.service.kafka.listener;

import java.util.Optional;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.queue.condition.KafkaCondition;
import com.oner365.queue.constants.QueueConstants;

/**
 * Kafka 监听服务
 *
 * @author zhaoyong
 */
@Component
@Conditional(KafkaCondition.class)
public class KafkaTaskLogListener {

    private final Logger logger = LoggerFactory.getLogger(KafkaTaskLogListener.class);

    @Resource
    private ISysTaskLogService sysTaskLogService;

    /**
     * 监听服务
     * @param consumerRecord 参数
     */
    @KafkaListener(id = QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, topics = { QueueConstants.SAVE_TASK_LOG_QUEUE_NAME })
    public void listener(ConsumerRecord<String, ?> consumerRecord) {
        Optional<?> kafkaMessage = Optional.of(consumerRecord.value());
        Object message = kafkaMessage.get();
        logger.info("Kafka saveExecuteTaskLog received: {}", message);

        // business
        SysTaskDto sysTask = JSON.parseObject(message.toString(), SysTaskDto.class);
        if (sysTask != null) {
            saveExecuteTaskLog(sysTask);
        }
    }

    public void saveExecuteTaskLog(SysTaskDto sysTask) {
        logger.info("saveExecuteTaskLog :{}", sysTask);

        long time = System.currentTimeMillis();
        SysTaskLogVo taskLog = new SysTaskLogVo();
        taskLog.setExecuteIp(HttpClientUtils.getLocalhost());
        taskLog.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
        taskLog.setStatus(TaskStatusEnum.NORMAL);
        taskLog.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
        taskLog.setTaskGroup(sysTask.getTaskGroup());
        taskLog.setTaskName(sysTask.getTaskName());
        taskLog.setInvokeTarget(sysTask.getInvokeTarget());
        taskLog.setCreateUser(sysTask.getCreateUser());
        sysTaskLogService.addTaskLog(taskLog);
    }

}
