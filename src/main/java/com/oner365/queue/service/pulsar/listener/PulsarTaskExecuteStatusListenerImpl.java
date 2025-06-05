package com.oner365.queue.service.pulsar.listener;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.PulsarCondition;

/**
 * pulsar UpdateTaskExecuteStatusDto listener
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(PulsarCondition.class)
public class PulsarTaskExecuteStatusListenerImpl implements MessageListener<UpdateTaskExecuteStatusDto>, BaseService {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(PulsarTaskExecuteStatusListenerImpl.class);

    @Resource
    private ISysTaskService sysTaskService;

    @Override
    public void received(Consumer<UpdateTaskExecuteStatusDto> consumer, Message<UpdateTaskExecuteStatusDto> msg) {
        try {
            String data = Arrays.toString(msg.getData());
            LOGGER.info("Pulsar consumer data: {}, topic: {}", data, consumer.getTopic());
            consumer.acknowledge(msg);
        }
        catch (PulsarClientException e) {
            consumer.negativeAcknowledge(msg);
        }
        // business
        UpdateTaskExecuteStatusDto updateTask = msg.getValue();
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
