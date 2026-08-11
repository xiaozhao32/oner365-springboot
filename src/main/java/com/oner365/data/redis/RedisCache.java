package com.oner365.data.redis;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Component;

import com.oner365.data.commons.util.GsonUtils;
import com.oner365.data.redis.constants.CacheConstants;

import jakarta.annotation.Resource;

/**
 * spring redis 工具类
 *
 * @author zhaoyong
 **/
@Component
public class RedisCache<T> {

    @Resource
    public RedisTemplate<String, T> redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param duration 时间
     */
    public void setCacheObject(final String key, final T value, final Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    /**
     * 设置有效时间
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final Duration duration) {
        return redisTemplate.expire(key, duration);
    }

    /**
     * 获得缓存的基本对象。
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public T getCacheObject(final String key, Class<T> clazz) {
        Object obj = redisTemplate.opsForValue().get(key);
        return GsonUtils.jsonToBean(GsonUtils.objectToJson(obj), clazz);
    }

    /**
     * 删除单个对象
     * @param key 键
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * @param collection 多个对象
     * @return long
     */
    public long deleteObject(final Collection<String> collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public long setCacheSet(final String key, final Set<T> dataSet) {
        @SuppressWarnings("unchecked")
        T[] array = dataSet.stream().toArray(size -> (T[]) new Object[size]);
        Long count = redisTemplate.opsForSet().add(key, array);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的set
     * @param key 键
     * @return Set
     */
    public Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     * @param key 键
     * @param dataMap 值
     */
    public void setCacheMap(final String key, final Map<Object, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     * @param key 键
     * @return Map
     */
    public Map<Object, Object> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public List<Object> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 获取客户端信息
     * @return List
     */
    public List<RedisClientInfo> getClientList() {
        return redisTemplate.getClientList();
    }

    /**
     * 分布式锁
     * @param key 键值
     * @param expireTime 过期时间(秒)
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public boolean lock(String key, long expireTime) {
        String lock = CacheConstants.CACHE_LOCK_NAME + key;
        return redisTemplate.opsForValue().setIfAbsent(lock, (T) lock, Expiration.from(expireTime, TimeUnit.SECONDS));
    }

}
