package com.oner365.data.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.redis.enums.RedisMode;

/**
 * Customer Redis properties
 * 
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisCacheProperties {

    /**
     * RedisMode default
     */
    private RedisMode mode = RedisMode.DEFAULT;

    public RedisMode getMode() {
        return mode;
    }

    public void setMode(RedisMode mode) {
        this.mode = mode;
    }

}
