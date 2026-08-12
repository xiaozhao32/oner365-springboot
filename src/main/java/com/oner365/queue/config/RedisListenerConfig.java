package com.oner365.queue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * 消息广播监听配置
 *
 * @author liutao
 */
@Configuration
public class RedisListenerConfig {

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter adapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 监听指定Topic 和 @RedisListener 一样
        container.addMessageListener(adapter, new PatternTopic(PublicConstants.NAME));
        return container;
    }

    @Bean
    MessageListenerAdapter customerMessageListener(CustomerRedisListener listener) {
        return new MessageListenerAdapter(listener);
    }

}
