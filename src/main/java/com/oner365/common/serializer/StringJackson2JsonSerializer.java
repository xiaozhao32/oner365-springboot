package com.oner365.common.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * StringJackson2JsonSerializer
 * @author zhaoyong
 */
public class StringJackson2JsonSerializer<T> implements RedisSerializer<T> {

    private final Class<T> clazz;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public StringJackson2JsonSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(Object t) {
        if (t == null) {
            return new byte[0];
        }
        try {
            return this.objectMapper.writeValueAsBytes(JSON.toJSON(t));
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        try {
            return this.objectMapper.readValue(bytes, clazz);
        } catch (Exception ex) {
            throw new SerializationException("Could not read bytes: " + ex.getMessage(), ex);
        }
    }

}
