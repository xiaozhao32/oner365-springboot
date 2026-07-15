package com.oner365.queue.config;

import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Redis Key Expire Listener
 *
 * @author zhaoyong
 *
 */
@Component
public class RedisKeyExpireListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(RedisKeyExpireListener.class);

    @Override
    public void onMessage(@Nonnull Message message, @Nonnull byte[] pattern) {
        // 获取过期的key
        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

        logger.info("MessageListener subscribe: {}, Channel: {}", expiredKey, channel);
    }

}
