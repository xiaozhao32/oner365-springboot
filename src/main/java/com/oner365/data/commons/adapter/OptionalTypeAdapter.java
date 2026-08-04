package com.oner365.data.commons.adapter;

import java.io.IOException;
import java.util.Optional;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.oner365.data.commons.util.GsonUtils;

/**
 * GSON Optional 判断
 * 
 * @author zhaoyong
 */
public class OptionalTypeAdapter extends TypeAdapter<Optional<?>> {
    
    @Override
    public void write(JsonWriter out, Optional<?> value) throws IOException {
        if (value.isPresent()) {
            // 将 Optional 中的值作为普通 JSON 元素输出
            GsonUtils.GSON.toJson(value.get(), Object.class, out);
        } else {
         // 如果 Optional 为空，输出 null
            out.nullValue();
        }
    }

    @Override
    public Optional<?> read(JsonReader in) throws IOException {
        // 读取时，如果为 null 则返回 Optional.empty()
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return Optional.empty();
        }
        // 否则读取值并包装为 Optional
        Object value = GsonUtils.GSON.fromJson(in, Object.class);
        return Optional.ofNullable(value);
    }
}
