package com.oner365.common.config;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oner365.common.config.properties.AccessTokenProperties;
import com.oner365.common.config.properties.IgnoreWhiteProperties;
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

  private final ApplicationEventPublisher publisher;

  private final IgnoreWhiteProperties ignoreWhiteProperties;

  private final AccessTokenProperties tokenProperties;

  /**
   * 构造方法
   * 
   * @param publisher             ApplicationEventPublisher
   * @param ignoreWhiteProperties IgnoreWhiteProperties
   * @param tokenProperties       AccessTokenProperties
   */
  public CurrentUserConfigurer(ApplicationEventPublisher publisher, IgnoreWhiteProperties ignoreWhiteProperties,
      AccessTokenProperties tokenProperties) {
    this.publisher = publisher;
    this.ignoreWhiteProperties = ignoreWhiteProperties;
    this.tokenProperties = tokenProperties;
  }

  /**
   * 注册拦截器
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**");
  }

  /**
   * token 拦截器
   * 
   * @return TokenInterceptor
   */
  @Bean
  public TokenInterceptor tokenInterceptor() {
    return new TokenInterceptor(publisher, ignoreWhiteProperties, tokenProperties);
  }

  /**
   * 注册参数
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(currentUserMethodArgumentResolver());
  }

  /**
   * CurrentUser 参数注解
   * 
   * @return CurrentUserMethodArgumentResolver
   */
  @Bean
  public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
    return new CurrentUserMethodArgumentResolver();
  }
}
