package com.oner365.queue.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * 监听消息
 *
 * @author zhaoyong
 *
 */
@Service
public class RedisMessageReceiver implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageReceiver.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messagePattern = new String(pattern);
        String messageBody = new String(message.getBody());
        LOGGER.info("Redis receiver pattern:{} message: {}", messagePattern, messageBody);
    }

}
