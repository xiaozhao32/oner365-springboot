package com.oner365.monitor.controller.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.util.DataUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.google.common.collect.Lists;

import redis.clients.jedis.Jedis;

/**
 * 缓存监控
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/monitor/cache")
@Api(tags = "监控 - 缓存")
public class CacheController extends BaseController {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String p;

    @Value("${spring.redis.port}")
    private int port;

    private static final int DB_LENGTH = 15;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 缓存信息
     * @return Map<String, Object>
     */
    @GetMapping("/index")
    @ApiOperation("缓存信息")
    public Map<String, Object> index() {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
        Properties commandStats = (Properties) redisTemplate
                .execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        
        if (commandStats != null) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }
        result.put("commandStats", pieList);
        return result;
    }

    /**
     * 缓存列表
     * @return List<Map<String, Object>>
     */
    @GetMapping("/cacheList")
    @ApiOperation("缓存列表")
    public List<Map<String, Object>> cacheList() {
        Jedis jedis = new Jedis(host, port);

        String auth = "ok";
        if (!DataUtils.isEmpty(p)) {
            auth = jedis.auth(p);
        } else {
            jedis.connect();
        }
        LOGGER.info("info: {}", auth);

        List<Map<String, Object>> result = Lists.newArrayList();
        if (jedis.isConnected()) {
            for (int i = 0; i <= DB_LENGTH; i++) {
                jedis.select(i);
                Long size = jedis.dbSize();
                if (size != 0L) {
                    Map<String, Object> map = new HashMap<>(3);
                    map.put("name", "DB" + i);
                    map.put("index", i);
                    map.put("size", size);
                    result.add(map);
                }
            }
        }
        jedis.close();

        return result;
    }

    /**
     * 清理缓存
     * @param index db
     * @return String
     */
    @GetMapping("/clean")
    @ApiOperation("清除缓存")
    public String clean(int index) {
        Jedis jedis = new Jedis(host, port);

        String auth = "ok";
        if (!DataUtils.isEmpty(p)) {
            auth = jedis.auth(p);
        } else {
            jedis.connect();
        }
        LOGGER.info("info: {}", auth);

        if (jedis.isConnected()) {
            jedis.select(index);
            jedis.flushDB();
        }
        jedis.close();
        return PublicConstants.SUCCESS;

    }

}
