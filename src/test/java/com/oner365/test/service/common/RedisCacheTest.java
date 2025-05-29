package com.oner365.test.service.common;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.oner365.data.redis.RedisCache;
import com.oner365.test.service.BaseServiceTest;

/**
 * 单元测试 - RedisCache
 *
 * @author zhaoyong
 */
@SpringBootTest
class RedisCacheTest extends BaseServiceTest {

    @Resource
    private RedisCache redisCache;

    @Test
    void getClientList() {
        List<String> result = redisCache.getClientList();
        Assertions.assertFalse(result.isEmpty());
        logger.info("result:{}", result);
    }

    @Test
    void cacheTest() {
        final String key = "k1";
        Integer value = 111;
        redisCache.setCacheObject(key, value);
        boolean expire = redisCache.expire(key, 10000);
        Assertions.assertTrue(expire);

        Collection<String> result = redisCache.keys(key);
        Assertions.assertFalse(result.isEmpty());
        logger.info("result1:{}", result);
        Boolean deleteResult = redisCache.deleteObject(key);
        logger.info("deleteResult1:{}", deleteResult);

        String key2 = "k2";
        Integer value2 = 222;
        redisCache.setCacheMapValue(key2, key2, value2);
        Integer result2 = redisCache.getCacheMapValue(key2, key2);
        logger.info("result2:{}", result2);

        List<Integer> list2 = redisCache.getMultiCacheMapValue(key2, Collections.singleton(key2));
        logger.info("list2:{}", list2);
        Long deleteResult2 = redisCache.deleteObject(Collections.singleton(key2));
        logger.info("deleteResult2:{}", deleteResult2);

        String key3 = "k3";
        Integer value3 = 333;
        Long result3 = redisCache.setCacheList(key3, Lists.newArrayList(value3));
        logger.info("result3:{}", result3);
        redisCache.deleteObject(key3);

        String key4 = "k4";
        Integer value4 = 444;
        Long result4 = redisCache.setCacheSet(key4, Sets.newHashSet(value4));
        logger.info("result4:{}", result4);
        redisCache.deleteObject(key4);
    }

}
