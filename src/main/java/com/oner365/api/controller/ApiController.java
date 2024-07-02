package com.oner365.api.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.cache.GuavaCache;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.datasource.util.DataSourceUtil;
import com.oner365.data.redis.RedisCache;
import com.oner365.data.web.controller.BaseController;
import com.oner365.data.web.sequence.sequence.RangeSequence;
import com.oner365.data.web.sequence.sequence.SnowflakeSequence;
import com.oner365.datasource.dynamic.DynamicDataSource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * API 接口测试
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "API公共接口")
@RequestMapping("/api")
public class ApiController extends BaseController {

  @Resource
  private RedisCache redisCache;

  @Resource
  private DynamicDataSource dataSource;

  @Resource
  private GuavaCache<Object> guavaCache;

  @Resource
  private SnowflakeSequence snowflakeSequence;

  @Resource
  private RangeSequence rangeSequence;

  @Resource
  private MessageSource messageSource;

  /**
   * 测试分库分表
   *
   * @param orderId 订单id
   * @param userId  用户id
   * @return List<Map<String, String>>
   */
  @ApiOperation("1.测试分库分表")
  @ApiOperationSupport(order = 1)
  @GetMapping("/sharding/test")
  public List<Map<String, String>> testDataSource(@RequestParam Integer orderId, @RequestParam Integer userId) {
    String sql = "insert into t_order(id, order_id, user_id, status, create_time) " + "values('"
        + snowflakeSequence.nextNo() + "'," + orderId + "," + userId + ",'1','" + DateUtil.getCurrentTime() + "')";
    try (Connection con = dataSource.getConnection()) {
      return DataSourceUtil.execute(con, sql);
    } catch (Exception e) {
      logger.error("testDataSource error:", e);
    }
    return Collections.emptyList();
  }

  /**
   * 测试guava cache
   *
   * @return ResponseResult<String>
   */
  @ApiOperation("2.测试Guava Cache")
  @ApiOperationSupport(order = 2)
  @GetMapping("/cache/guava/test")
  public ResponseResult<String> testGuavaCache() {
    String sequence1 = snowflakeSequence.nextNo();
    logger.info("sequence1: {}", sequence1);
    String sequence2 = rangeSequence.nextNo();
    logger.info("sequence2: {}", sequence2);

    String key = "a1";
    if (DataUtils.isEmpty(guavaCache.getCache(key))) {
      guavaCache.setCache(key, Optional.of(111));
    }
    logger.info("cache:{}", guavaCache.getCache(key));
    logger.info("cache:{}", guavaCache.getCache(key));
    guavaCache.removeCache(key);

    return ResponseResult.success(ResultEnum.SUCCESS.getName());
  }

  /**
   * 测试redis
   *
   * @return JSONObject
   */
  @ApiOperation("3.测试Redis Cache")
  @ApiOperationSupport(order = 3)
  @GetMapping("/cache/redis/test")
  public JSONObject testRedisCache() {
    String key = "test1";
    JSONObject value = new JSONObject();
    value.put("aaa", 111);
    value.put("bbb", 222);
    redisCache.setCacheObject(key, value, PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);
    JSONObject json = redisCache.getCacheObject(key);
    logger.info("test1:{}", json);

    String key2 = "test2";
    List<Map<String, Object>> dataList = new ArrayList<>();
    Map<String, Object> m1 = new HashMap<>(3);
    m1.put("a1", "a11");
    m1.put("a2", "a22");
    m1.put("a3", "a33");
    dataList.add(m1);
    Map<String, Object> m2 = new HashMap<>(3);
    m2.put("b1", "b11");
    m2.put("b2", "b22");
    m2.put("b3", "b33");
    dataList.add(m2);
    Map<String, Object> m3 = new HashMap<>(3);
    m3.put("c1", "c11");
    m3.put("c2", "c22");
    m3.put("c3", "c33");
    dataList.add(m3);
    redisCache.deleteObject(key2);
    redisCache.setCacheList(key2, dataList);
    redisCache.expire(key2, PublicConstants.EXPIRE_TIME);
    List<String> list = redisCache.getCacheList(key2);
    logger.info("test2:{}", list);

    String key3 = "test3";
    Map<String, Object> dataMap = new HashMap<>(3);
    dataMap.put("ddd", dataList);
    redisCache.setCacheMap(key3, dataMap);
    redisCache.expire(key3, PublicConstants.EXPIRE_TIME);
    Map<String, Object> map = redisCache.getCacheMap(key3);
    logger.info("test3:{}", map);

    String key4 = "test4";
    Set<String> dataSet = new HashSet<>();
    dataSet.add("aaa");
    dataSet.add("bbb");
    redisCache.setCacheSet(key4, dataSet);
    redisCache.expire(key4, PublicConstants.EXPIRE_TIME);
    Set<String> set = redisCache.getCacheSet(key4);
    logger.info("test4:{}", set);

    String key5 = "test5";
    boolean b1 = redisCache.lock(key5, 10);
    logger.info("test5 lock:{}", b1);
    boolean b2 = redisCache.lock(key5, 10);
    logger.info("test5 lock:{}", b2);

    return json;
  }

  /**
   * 测试redis
   *
   * @return JSONObject
   */
  @ApiOperation("4.测试国际化")
  @ApiOperationSupport(order = 4)
  @GetMapping("/i18n/messages")
  public JSONObject testMessages() {
    Locale locale = LocaleContextHolder.getLocale();
    String name = messageSource.getMessage("hello", null, locale);
    
    JSONObject result = new JSONObject();
    result.put("language", locale.toLanguageTag());
    result.put("name", name);
    return result;
  }
}
