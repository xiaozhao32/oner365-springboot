package com.oner365.api.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.cache.GuavaCache;
import com.oner365.common.cache.RedisCache;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.datasource.util.DataSourceUtil;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;
import com.oner365.util.SnowFlakeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * API 接口测试
 * @author zhaoyong
 */
@RestController
@RequestMapping("/api")
@Api(tags = "API公共接口")
public class ApiController extends BaseController {

    @Autowired
    private RedisCache redisCache;

    @Resource(name = "dataSource")
    private DataSource shardingDataSource;

    @Autowired
    private GuavaCache<Object> guavaCache;

    /**
     * 测试分库分表
     * @param orderId 订单id
     * @param userId 用户id
     * @return List<Map<String, String>>
     */
    @GetMapping("/testDataSource")
    @ApiOperation("测试分库分表")
    public List<Map<String, String>> testDataSource(@RequestParam Integer orderId, @RequestParam Integer userId) {
        String sql = "insert into t_order(id, order_id, user_id, status, create_time) " + "values('"
                + new SnowFlakeUtils(1L, 1L).nextId() + "'," + orderId + "," + userId + ",'1','"+DateUtil.getCurrentTime()+"')";
        List<Map<String, String>> result = new ArrayList<>();
        try (Connection con = shardingDataSource.getConnection()) {
            result = DataSourceUtil.execute(con, sql);
        } catch (Exception e) {
            LOGGER.error("testDataSource error:", e);
        }
        return result;
    }

    /**
     * 测试guava cache
     * @return String
     */
    @GetMapping("/testGuavaCache")
    @ApiOperation("测试Guava Cache")
    public String testGuavaCache() {
        String key = "a1";
        if (DataUtils.isEmpty(guavaCache.getCache(key))) {
            guavaCache.setCache(key, Optional.of(111));
        }
        LOGGER.info("cache:{}", guavaCache.getCache(key));
        LOGGER.info("cache:{}", guavaCache.getCache(key));
        guavaCache.removeCache(key);

        return PublicConstants.SUCCESS;
    }

    /**
     * 测试redis
     * @return String
     */
    @GetMapping("/testRedisCache")
    @ApiOperation("测试Redis Cache")
    public String testRedisCache() {
        String key = "test1";
        JSONObject value = new JSONObject();
        value.put("aaa", 111);
        value.put("bbb", 222);
        redisCache.setCacheObject(key, value, PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);
        JSONObject json = redisCache.getCacheObject(key);
        LOGGER.info("test1:{}", json);

        String key2 = "test2";
        List<Map<String, Object>> dataList = Lists.newArrayList();
        Map<String, Object> m1 = Maps.newHashMap();
        m1.put("a1", "a11");
        m1.put("a2", "a22");
        m1.put("a3", "a33");
        dataList.add(m1);
        Map<String, Object> m2 = Maps.newHashMap();
        m2.put("b1", "b11");
        m2.put("b2", "b22");
        m2.put("b3", "b33");
        dataList.add(m2);
        Map<String, Object> m3 = Maps.newHashMap();
        m3.put("c1", "c11");
        m3.put("c2", "c22");
        m3.put("c3", "c33");
        dataList.add(m3);
        redisCache.deleteObject(key2);
        redisCache.setCacheList(key2, dataList);
        redisCache.expire(key2, PublicConstants.EXPIRE_TIME);
        List<String> list = redisCache.getCacheList(key2);
        LOGGER.info("test2:{}", list);

        String key3 = "test3";
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("ddd", dataList);
        redisCache.setCacheMap(key3, dataMap);
        redisCache.expire(key3, PublicConstants.EXPIRE_TIME);
        Map<String, Object> map = redisCache.getCacheMap(key3);
        LOGGER.info("test3:{}", map);

        String key4 = "test4";
        Set<String> dataSet = Sets.newHashSet();
        dataSet.add("aaa");
        dataSet.add("bbb");
        redisCache.setCacheSet(key4, dataSet);
        redisCache.expire(key4, PublicConstants.EXPIRE_TIME);
        Set<String> set = redisCache.getCacheSet(key4);
        LOGGER.info("test4:{}", set);

        String key5 = "test5";
        boolean b1 = redisCache.lock(key5, 10000);
        LOGGER.info("test5 lock:{}", b1);
        boolean b2 = redisCache.lock(key5, 10000);
        LOGGER.info("test5 lock:{}", b2);

        return PublicConstants.SUCCESS;
    }
}
