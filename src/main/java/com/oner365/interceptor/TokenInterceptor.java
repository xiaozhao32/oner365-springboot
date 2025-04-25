package com.oner365.interceptor;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.oner365.data.commons.config.properties.AccessTokenProperties;
import com.oner365.data.commons.config.properties.IgnoreWhiteProperties;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.util.JwtUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Token拦截器
 *
 * @author zhaoyong
 *
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);

  @Resource
  private ApplicationEventPublisher publisher;

  @Resource
  private IgnoreWhiteProperties ignoreWhiteProperties;
  
  @Resource
  private AccessTokenProperties tokenProperties;

  /**
   * Handler
   */
  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object object) {

    // 验证白名单
    if (validateIgnoreWhites(request)) {
      return true;
    }

    // 验证token
    if (validateToken(request)) {
      return true;
    }

    // 返回错误信息
    return setUnauthorizedResponse(request, response);
  }

  /**
   * 返回错误消息
   * 
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @return boolean
   */
  private boolean setUnauthorizedResponse(HttpServletRequest request, HttpServletResponse response) {
    ResponseData<Serializable> responseData = ResponseData.error(HttpStatus.UNAUTHORIZED.value(),
        HttpStatus.UNAUTHORIZED.name());
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    // 写出
    try {
      // 返回错误消息
      LOGGER.error("[{}] Client Unauthorized error. Request uri: {}", HttpStatus.UNAUTHORIZED.value(), request.getRequestURI());
      response.getOutputStream().write(JSON.toJSONString(responseData).getBytes());
    } catch (IOException e) {
      LOGGER.error("TokenInterceptor setUnauthorizedResponse error", e);
    }
    return false;
  }

  /**
   * 验证白名单
   *
   * @param request HttpServletRequest
   * @return boolean
   */
  private boolean validateIgnoreWhites(HttpServletRequest request) {
    if (PublicConstants.DELIMITER.equals(request.getRequestURI())) {
      return true;
    }
    List<String> paths = ignoreWhiteProperties.getWhites();
    return paths.stream().anyMatch(request.getRequestURI()::contains);
  }

  /**
   * 验证token
   *
   * @param request HttpServletRequest
   * @return boolean
   */
  private boolean validateToken(HttpServletRequest request) {
    try {
      String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
      return JwtUtils.validateToken(auth, tokenProperties.getSecret());
    } catch (Exception e) {
      LOGGER.error("TokenInterceptor validateToken error: {}", request.getRequestURI(), e);
    }
    return false;
  }

}
