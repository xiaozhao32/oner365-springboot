package com.oner365.data.commons.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.enums.QueueEnum;

/**
 * 队列配置
 *
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "queue")
public class DefaultQueueProperties {

    /**
     * QueueEnum default rabbitmq
     */
    private QueueEnum type = QueueEnum.RABBITMQ;

    public QueueEnum getType() {
        return type;
    }

    public void setType(QueueEnum type) {
        this.type = type;
    }

}
