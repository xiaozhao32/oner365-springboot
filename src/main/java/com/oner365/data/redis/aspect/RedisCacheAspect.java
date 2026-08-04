package com.oner365.data.redis.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.oner365.data.commons.config.properties.AccessTokenProperties;
import com.oner365.data.commons.config.properties.CommonProperties;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.ClassesUtil;
import com.oner365.data.commons.util.GsonUtils;
import com.oner365.data.redis.annotation.RedisCacheAble;
import com.oner365.data.redis.annotation.RedisCacheEvict;
import com.oner365.data.redis.annotation.RedisCachePut;
import com.oner365.data.redis.util.RedisUtils;

import jakarta.annotation.Resource;

/**
 * Cache Aspect
 *
 * @author zhaoyong
 *
 */
@Aspect
@Component
public class RedisCacheAspect {

    /**
     * redis缓存开关
     */
    @Resource
    private CommonProperties commonProperties;

    @Resource
    private AccessTokenProperties accessTokenProperties;

    @Pointcut("@annotation(com.oner365.data.redis.annotation.RedisCachePut)")
    public void annotationPut() {
        // RedisCachePut
    }

    @Pointcut("@annotation(com.oner365.data.redis.annotation.RedisCacheAble)")
    public void annotationAble() {
        // RedisCacheAble
    }

    @Pointcut("@annotation(com.oner365.data.redis.annotation.RedisCacheEvict)")
    public void annotationEvict() {
        // RedisCacheEvict
    }

    /**
     * annotationAble
     * @param joinPoint ProceedingJoinPoint
     * @param rd RedisCacheAble
     * @return Object
     * @throws Throwable 异常
     */
    @Around("annotationAble()&& @annotation(rd)")
    public Object redisCacheAble(ProceedingJoinPoint joinPoint, RedisCacheAble rd) throws Throwable {

        String key = PublicConstants.EMPTY;
        if (commonProperties.isRedisEnabled()) {
            String preKey = rd.value();
            String arg0 = joinPoint.getArgs()[0].toString();

            Class<?> returnClassType = ((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType();
            key = preKey + "::" + arg0;
            String rtObject = RedisUtils.getCacheObject(key);

            // Return Cache
            if (rtObject != null) {
                return GsonUtils.jsonToBean(rtObject, returnClassType);
            }
        }

        Object sourceObject = joinPoint.proceed();
        if (sourceObject == null) {
            return null;
        }

        if (commonProperties.isRedisEnabled()) {
            // Set cache
            RedisUtils.setCacheObject(key, GsonUtils.objectToJson(sourceObject), accessTokenProperties.getExpireTime());
        }
        return sourceObject;
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
            RedisUtils.deleteObject(key);
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
            RedisUtils.deleteObject(key);

            // Set cache
            RedisUtils.setCacheObject(key, GsonUtils.objectToJson(resultValue), accessTokenProperties.getExpireTime());
        }
    }

    private static String getRedisKey(RedisCachePut rd, Object sourceObject) {
        String key = rd.key();
        key = key.substring(1);

        String firstLetter = key.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + key.substring(1);
        Object keyValue = ClassesUtil.invokeMethod(sourceObject, getter);

        return rd.value() + "::" + keyValue.toString();
    }

}
