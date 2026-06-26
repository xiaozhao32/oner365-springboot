package com.oner365.data.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.redis.enums.RedisMode;

import redis.clients.jedis.Jedis;

/**
 * Jedis 工具类
 *
 * @author zhaoyong
 *
 */
public class JedisUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisUtils.class);

    private JedisUtils() {
    }

    /**
     * 获取 Jedis
     * 
     * @param redisProperties 属性文件
     * @param mode            RedisMode.DEFAULT
     * @return Jedis
     */
    public static Jedis getJedis(DataRedisProperties redisProperties, RedisMode mode) {
        if (!RedisMode.DEFAULT.equals(mode)) {
            LOGGER.error("Only Jedis Connection.");
            return null;
        }
        // default
        Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());
        return getConnect(jedis, redisProperties.getPassword());
    }

    private static Jedis getConnect(Jedis jedis, String password) {
        String auth = "ok";
        if (!DataUtils.isEmpty(password)) {
            auth = jedis.auth(password);
        } else {
            jedis.connect();
        }
        LOGGER.debug("info: {}", auth);
        return jedis;
    }

}
