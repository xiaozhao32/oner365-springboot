package com.oner365.data.commons.adapter;

import java.io.StringReader;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

/**
 * jakarta.json.Json 解析器
 *
 * @author zhaoyong
 *
 */
public class JakartaJsonObjectTypeAdapter implements JsonSerializer<JsonObject>, JsonDeserializer<JsonObject> {

    @Override
    public JsonElement serialize(JsonObject src, Type typeOfSrc, JsonSerializationContext context) {
        // JsonObject -> JSON 字符串 -> JsonElement
        String jsonStr = src.toString();
        return JsonParser.parseString(jsonStr);
    }

    @Override
    public JsonObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // JsonElement -> JSON 字符串 -> JsonObject
        String jsonStr = json.toString();
        try (JsonReader reader = Json.createReader(new StringReader(jsonStr))) {
            return reader.readObject();
        }
        catch (Exception e) {
            throw new JsonParseException("Failed to deserialize JsonObject", e);
        }
    }

}
