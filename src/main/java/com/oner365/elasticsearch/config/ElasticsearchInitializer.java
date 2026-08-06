package com.oner365.elasticsearch.config;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.oner365.elasticsearch.converter.LocalDateTimeToStringConverter;
import com.oner365.elasticsearch.converter.StringToLocalDateTimeConverter;

/**
 * Elasticsearch 初始化
 *
 * @author zhaoyong
 *
 */
@Order(2)
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.oner365.elasticsearch.repository")
public class ElasticsearchInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
    
    @Bean
    ElasticsearchCustomConversions elasticsearchCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new LocalDateTimeToStringConverter());
        converters.add(new StringToLocalDateTimeConverter());
        return new ElasticsearchCustomConversions(converters);
    }

}
