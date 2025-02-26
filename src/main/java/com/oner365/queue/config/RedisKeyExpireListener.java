package com.oner365.queue.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * Redis Key Expire Listener
 *
 * @author zhaoyong
 *
 */
@Component
public class RedisKeyExpireListener extends KeyExpirationEventMessageListener {

  private final static Logger logger = LoggerFactory.getLogger(RedisKeyExpireListener.class);

  public RedisKeyExpireListener(RedisMessageListenerContainer listenerContainer) {
    super(listenerContainer);
  }

  @Override
  public void doHandleMessage(Message message) {
    if (message != null) {
      String body = new String(message.getBody());
      String channel = new String(message.getChannel());
      logger.info("RedisKeyExpireListener message: {}, channel: {}", body, channel);
    }
  }

}
