package com.oner365.monitor.controller.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.util.DataUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;

/**
 * 缓存监控
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "监控 - 缓存")
@RequestMapping("/monitor/cache")
public class CacheController extends BaseController {

  private static final int DB_LENGTH = 15;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private RedisProperties redisProperties;
  
  /**
   * 缓存信息
   * 
   * @return Map<String, Object>
   */
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
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
   * 
   * @return List<Map<String, Object>>
   */
  @ApiOperation("2.缓存列表")
  @ApiOperationSupport(order = 2)
  @GetMapping("/list")
  public List<Map<String, Object>> cacheList() {
    Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());

    String auth = "ok";
    if (!DataUtils.isEmpty(redisProperties.getPassword())) {
      auth = jedis.auth(redisProperties.getPassword());
    } else {
      jedis.connect();
    }
    LOGGER.debug("info: {}", auth);

    List<Map<String, Object>> result = new ArrayList<>();
    if (jedis.isConnected()) {
      IntStream.range(0, DB_LENGTH).forEach(i -> {
        jedis.select(i);
        Long size = jedis.dbSize();
        if (size != 0L) {
          Map<String, Object> map = new HashMap<>(3);
          map.put("name", "DB" + i);
          map.put("index", i);
          map.put("size", size);
          result.add(map);
        }
      });
    }
    jedis.close();

    return result;
  }

  /**
   * 清理缓存
   * 
   * @param index db
   * @return String
   */
  @ApiOperation("3.清除缓存")
  @ApiOperationSupport(order = 3)
  @GetMapping("/clean")
  public String clean(int index) {
    Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());

    String auth = "ok";
    if (!DataUtils.isEmpty(redisProperties.getPassword())) {
      auth = jedis.auth(redisProperties.getPassword());
    } else {
      jedis.connect();
    }
    LOGGER.debug("info: {}", auth);

    if (jedis.isConnected()) {
      jedis.select(index);
      jedis.flushDB();
    }
    jedis.close();
    return ResultEnum.SUCCESS.getName();

  }

}
