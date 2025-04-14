package com.oner365.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oner365.data.commons.constants.PublicConstants;

import jakarta.annotation.Resource;

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

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowCredentials(true)
        .allowedHeaders(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
        .allowedOriginPatterns("*").exposedHeaders(PublicConstants.NAME).maxAge(3600);
  }

}
