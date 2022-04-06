package com.oner365.log.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;

/**
 * Logger 日志初始化
 *
 * @author zhaoyong
 *
 */
@Order(1)
@Configuration
public class ApplicationLoggerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * initialize logger
     */
    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        // 配置 logback 这里配置无效
    }
}
