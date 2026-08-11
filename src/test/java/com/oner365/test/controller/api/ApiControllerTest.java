package com.oner365.test.controller.api;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.redis.RedisCache;
import com.oner365.data.redis.constants.CacheConstants;
import com.oner365.data.redis.util.RedisUtils;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.test.controller.BaseControllerTest;

import jakarta.annotation.Resource;

/**
 * Test ApiController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest extends BaseControllerTest {

    private static final String PATH = "/api";

    @Resource
    private RedisCache<LoginUserDto> redisCache;

    @RepeatedTest(2)
    void cacheRedis() {
        String url = PATH + "/cache/redis/test";
        Object result = get(url);
        logger.info("cacheRedis:[{}] -> {}", url, result);
        Assertions.assertEquals("abc", result.toString());

        // PO
        LoginUserDto dto = new LoginUserDto();
        dto.setAccessToken("token");
        dto.setExpireTime(System.currentTimeMillis());
        dto.setRealName(PublicConstants.NAME);
        dto.setUserId("1");
        dto.setIsAdmin("admin");
        dto.setAvatar(null);
        dto.setRoles(Collections.emptyList());
        dto.setJobs(Collections.emptyList());
        dto.setOrgs(Collections.emptyList());

        String key = CacheConstants.CACHE_LOGIN_NAME + dto.getRealName();

        Duration duration = Duration.ofMinutes(1L);
        logger.info("duration: units: {}, seconds: {}", TimeUnit.SECONDS, duration.getSeconds());

        DataSize dataSize = DataSize.of(1L, DataUnit.GIGABYTES);
        logger.info("dataSize: {}", dataSize.toBytes());

        // Redisson
        RedisUtils.setCacheObject(key, dto, duration);
        LoginUserDto cacheUser = RedisUtils.getCacheObject(key);
        String cacheUserString = objectMapper.writeValueAsString(cacheUser);
        logger.info("redisson: {}", cacheUserString);
        Assertions.assertNotNull(cacheUser);

    }

}
