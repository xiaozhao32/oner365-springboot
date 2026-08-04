package com.oner365.data.redis.serializer;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.oner365.data.commons.util.GsonUtils;

/**
 * 自定义 JsonObject Redis 序列化器 专门用于序列化/反序列化 JsonObject
 * 
 * @author zhaoyong
 * 
 */
public class JsonObjectRedisSerializer<T> implements RedisSerializer<T> {
    
    private final Class<T> targetClass;
    
    private final Type targetType;
    
    // 使用 Class
    public JsonObjectRedisSerializer(Class<T> targetClass) {
        this.targetClass = targetClass;
        this.targetType = null;
    }

    // 使用 Type（支持泛型）
    public JsonObjectRedisSerializer(Type targetType) {
        this.targetType = targetType;
        this.targetClass = null;
    }

    @Override
    public @Nonnull byte[] serialize(@Nullable T object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        }

        try {
            // Gson 解析
            String jsonString = GsonUtils.objectToJson(object, object.getClass());
            return jsonString.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SerializationException("Failed to serialize JsonObject", e);
        }
    }

    @Override
    public T deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        String jsonString = new String(bytes, StandardCharsets.UTF_8);
        try {
            // 解析为 Gson
            if (targetType != null) {
                return GsonUtils.jsonToBean(jsonString, targetType);
            } else {
                return GsonUtils.jsonToBean(jsonString, targetClass);
            }
        } catch (Exception e) {
            throw new SerializationException("Failed to deserialize object", e);
        }
    }
}