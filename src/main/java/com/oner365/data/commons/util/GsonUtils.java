package com.oner365.data.commons.util;

import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oner365.data.commons.adapter.JakartaJsonObjectTypeAdapter;
import com.oner365.data.commons.adapter.OptionalTypeAdapter;

import jakarta.json.JsonObject;

/**
 * Gson工具类
 *
 * @author zhaoyong
 */
public class GsonUtils {

    private GsonUtils() {

    }

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(JsonObject.class, new JakartaJsonObjectTypeAdapter())
            .registerTypeAdapter(Optional.class, new OptionalTypeAdapter())
            .create();

    /***
     * 把对象转化成JSON
     * 
     * @param obj 对象
     * @return String
     */
    public static String objectToJson(Object obj) {
        return GSON.toJson(obj);
    }
    
    public static <T> String objectToJson(Object obj, Class<T> clazz) {
        return GSON.toJson(obj, clazz);
    }

    /***
     * JSON转对象类型
     * 
     * @param json  json字符串
     * @param clazz 类
     * @return T
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /***
     * JSON转对象类型
     * 
     * @param json json字符串
     * @param type type
     * @return T
     */
    public static <T> T jsonToBean(String json, Type type) {
        return GSON.fromJson(json, type);
    }

}
