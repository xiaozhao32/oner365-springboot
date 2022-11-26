package com.oner365.elasticsearch.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

/**
 * Elasticsearch 初始化
 *
 * @author zhaoyong
 *
 */
@Order(2)
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.oner365.elasticsearch.dao")
public class ElasticsearchInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
    System.setProperty("es.set.netty.runtime.available.processors", "false");
  }

}
