package com.oner365.data.web.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oner365.data.web.resolver.CurrentUserMethodArgumentResolver;
import com.oner365.interceptor.TokenInterceptor;

import jakarta.annotation.Resource;

/**
 * 注册用户信息
 * 
 * @author zhaoyong
 */
@Configuration
public class CurrentUserConfigurer implements WebMvcConfigurer {

  @Resource
  private CurrentUserMethodArgumentResolver resolver;
  
  @Resource
  private TokenInterceptor tokenInterceptor;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(resolver);
  }
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
  }
  
}
