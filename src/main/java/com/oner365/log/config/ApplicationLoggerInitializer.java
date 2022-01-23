package com.oner365.log.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

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
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        String logBase = environment.getProperty("logging.file.path", "logs");
        System.setProperty("logging.file.name", String.format("%s/%s/debug.log", logBase, appName));
    }
}
