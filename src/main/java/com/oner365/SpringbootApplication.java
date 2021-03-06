package com.oner365;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 主函数启动服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan({ "com.oner365.**.mapper" })
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringbootApplication.class);
        application.run(args);
    }

}
