package com.oner365.common.cache.aspect;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.oner365.common.cache.RedisCache;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCacheEvict;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.config.properties.CommonProperties;
import com.oner365.common.constants.PublicConstants;
import com.oner365.util.ClassesUtil;

/**
 * Cache Aspect
 * @author zhaoyong
 *
 */
@Aspect
@Component
public class RedisCacheAspect {
  
    private final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Resource
    private RedisCache redisCache;

    @Resource
    private CommonProperties commonProperties;

    @Pointcut("@annotation(com.oner365.common.cache.annotation.RedisCachePut)")
    public void annotationPut() {
        // put
    }

    @Pointcut("@annotation(com.oner365.common.cache.annotation.RedisCacheAble)")
    public void annotationAble() {
        // cache able
    }

    @Pointcut("@annotation(com.oner365.common.cache.annotation.RedisCacheEvict)")
    public void annotationEvict() {
        // evict
    }

    /**
     * annotationAble
     *
     * @param joinPoint ProceedingJoinPoint
     * @param rd RedisCacheAble
     * @return Object
     */
    @Around("annotationAble()&& @annotation(rd)")
    public Object redisCacheAble(ProceedingJoinPoint joinPoint, RedisCacheAble rd) {

        String key = "";
        if (commonProperties.isRedisEnabled()) {
            String preKey = rd.value();
            String arg0 = joinPoint.getArgs()[0].toString();

            Class<?> returnClassType = ((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType();
            key = preKey + "::" + arg0;
            String rtObject = redisCache.getCacheObject(key);

            // Return Cache
            if (rtObject != null) {
                return JSON.parseObject(rtObject, returnClassType);
            }
        }

        try {
          Object sourceObject = joinPoint.proceed();
          if (sourceObject == null) {
            return null;
          }
  
          if (commonProperties.isRedisEnabled()) {
              // Set cache
              redisCache.setCacheObject(key, JSON.toJSONString(sourceObject), PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);
          }
          return sourceObject;
        } catch (Throwable e) {
          logger.error("redisCacheAble error:", e);
        }
        return null;
    }

    /**
     * annotationEvict
     * @param joinPoint JoinPoint
     * @param rd RedisCacheEvict
     */
    @After("annotationEvict()&& @annotation(rd)")
    public void redisCacheEvict(JoinPoint joinPoint, RedisCacheEvict rd) {
        if (commonProperties.isRedisEnabled()) {
            String preKey = rd.value();
            String arg0 = joinPoint.getArgs()[0].toString();

            String key = preKey + "::" + arg0;
            redisCache.deleteObject(key);
        }
    }

    /**
     * annotationPut
     * @param joinPoint JoinPoint
     * @param resultValue Object
     * @param rd RedisCachePut
     */
    @AfterReturning(returning = "resultValue", pointcut = "annotationPut()&& @annotation(rd)")
    public void redisCachePut(JoinPoint joinPoint, Object resultValue, RedisCachePut rd) {

        if (resultValue == null) {
            return;
        }
        if (commonProperties.isRedisEnabled()) {
            String key = getRedisKey(rd, resultValue);
            redisCache.deleteObject(key);

            // Set cache
            redisCache.setCacheObject(key, JSON.toJSONString(resultValue), PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);
        }
    }

    private static String getRedisKey(RedisCachePut rd, Object sourceObject) {
        String key = rd.key();
        key = key.substring(1);

        String firstLetter = key.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + key.substring(1);
        Object keyValue = ClassesUtil.invokeMethod(sourceObject, getter);

        assert keyValue != null;
        return rd.value() + "::" + keyValue.toString();
    }

}
