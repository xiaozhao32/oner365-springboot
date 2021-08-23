package com.oner365;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.oner365.log.init.ApplicationLoggerInitializer;

/**
 * 主函数启动服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@ServletComponentScan
@MapperScan({ "com.oner365.**.mapper" })
@EnableElasticsearchRepositories(basePackages = "com.oner365.elasticsearch.service")
public class SpringbootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication application = new SpringApplication(SpringbootApplication.class);
        application.addInitializers(new ApplicationLoggerInitializer());
        application.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootApplication.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
