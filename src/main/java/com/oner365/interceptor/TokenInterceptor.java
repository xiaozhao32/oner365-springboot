package com.oner365.interceptor;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.oner365.common.ResponseData;
import com.oner365.common.config.properties.AccessTokenProperties;
import com.oner365.common.config.properties.IgnoreWhiteProperties;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.jwt.JwtUtils;
import com.oner365.log.event.SysLogEvent;
import com.oner365.sys.vo.SysLogVo;
import com.oner365.util.DataUtils;

/**
 * Token拦截器
 *
 * @author zhaoyong
 *
 */
@Order(3)
@Configuration
public class TokenInterceptor implements HandlerInterceptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);

  private final ApplicationEventPublisher publisher;

  private final IgnoreWhiteProperties ignoreWhiteProperties;
  
  private final AccessTokenProperties tokenProperties;

  /**
   * Constructor
   *
   * @param publisher ApplicationEventPublisher
   */
  public TokenInterceptor(ApplicationEventPublisher publisher, 
      IgnoreWhiteProperties ignoreWhiteProperties, AccessTokenProperties tokenProperties) {
    this.publisher = publisher;
    this.ignoreWhiteProperties = ignoreWhiteProperties;
    this.tokenProperties = tokenProperties;
  }

  /**
   * Handler
   */
  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object object) {

    // 记录请求日志
    requestLog(request);

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

  /**
   * 记录日志
   *
   * @param request HttpServletRequest
   */
  private void requestLog(HttpServletRequest request) {
    // 请求ip
    String ip = DataUtils.getIpAddress(request);
    // 请求地址
    String uri = request.getRequestURI();
    // 请求方法
    String methodName = request.getMethod();
    // 除get请求一律保存日志
    if (!HttpMethod.GET.matches(methodName)) {
      SysLogVo sysLog = new SysLogVo();
      sysLog.setCreateTime(LocalDateTime.now());
      sysLog.setMethodName(methodName);
      sysLog.setOperationIp(ip);
      sysLog.setOperationPath(uri);
      sysLog.setOperationName(StringUtils.substringBefore(uri.substring(1), PublicConstants.DELIMITER));
      this.publisher.publishEvent(new SysLogEvent(sysLog));
    }
  }

}
