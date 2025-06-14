package com.oner365.queue.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Kafka配置类
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    /**
     * topic
     */
    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    /**
     * group
     */
    @Value("${spring.kafka.consumer.group-id}")
    private String group;

    public KafkaProperties() {
        super();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
