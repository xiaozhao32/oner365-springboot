package com.oner365.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oner365.common.resolver.CurrentUserMethodArgumentResolver;
import com.oner365.interceptor.TokenInterceptor;

/**
 * 构造拦截器
 * 
 * @author zhaoyong
 *
 */
@Configuration
public class CurrentUserConfigurer implements WebMvcConfigurer {

  private final CurrentUserMethodArgumentResolver resolver;

  private final TokenInterceptor tokenInterceptor;

  /**
   * 构造方法
   * 
   * @param resolver         CurrentUserMethodArgumentResolver
   * @param tokenInterceptor TokenInterceptor
   */
  public CurrentUserConfigurer(CurrentUserMethodArgumentResolver resolver, TokenInterceptor tokenInterceptor) {
    this.resolver = resolver;
    this.tokenInterceptor = tokenInterceptor;
  }

  /**
   * 注册拦截器
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
  }

  /**
   * 注册参数
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(resolver);
  }

}
