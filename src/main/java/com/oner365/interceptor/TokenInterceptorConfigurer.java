package com.oner365.interceptor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册用户信息
 * 
 * @author zhaoyong
 */
@Configuration
public class TokenInterceptorConfigurer implements WebMvcConfigurer {

  @Resource
  private TokenInterceptor tokenInterceptor;
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
  }

}
