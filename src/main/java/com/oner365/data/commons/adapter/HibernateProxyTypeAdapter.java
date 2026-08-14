package com.oner365.data.commons.adapter;

import org.hibernate.proxy.HibernateProxy;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Hibernate 代理对象的 Gson 类型适配器
 * 
 * @author zhaoyong
 * 
 */
public class HibernateProxyTypeAdapter extends TypeAdapter<Object> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @Override
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return (HibernateProxy.class.isAssignableFrom(type.getRawType())
                    ? (TypeAdapter<T>) new HibernateProxyTypeAdapter(gson)
                    : null);
        }
    };

    private final Gson context;

    private HibernateProxyTypeAdapter(Gson gson) {
        this.context = gson;
    }

    @Override
    public void write(JsonWriter out, Object value) throws java.io.IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        // 如果值是 HibernateProxy，获取其真实实现对象
        Object resolved;
        if (value instanceof HibernateProxy hibernateProxy) {
            resolved = hibernateProxy.getHibernateLazyInitializer().getImplementation();
        } else {
            resolved = value;
        }

        // 使用 Gson 的默认适配器来序列化解代理后的对象
        context.getAdapter(Object.class).write(out, resolved);
    }

    @Override
    public Object read(JsonReader in) throws java.io.IOException {
        // 反序列化不需要特殊处理
        return context.getAdapter(Object.class).read(in);
    }
}
