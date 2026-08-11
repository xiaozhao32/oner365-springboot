package com.oner365.queue.config;

import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Customer Redis Listener
 *
 * @author zhaoyong
 *
 */
@Component
public class CustomerRedisListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(CustomerRedisListener.class);

    @Override
    public void onMessage(@Nonnull Message message, @Nonnull byte[] pattern) {
        // Listener
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

        logger.info("MessageListener subscribe: {}, Channel: {}", body, channel);
    }

}
