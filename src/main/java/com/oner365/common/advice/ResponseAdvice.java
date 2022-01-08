package com.oner365.common.advice;

import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oner365.common.ResponseData;
import com.oner365.common.config.properties.ClientWhiteProperties;
import com.oner365.util.Cipher;
import com.oner365.util.DataUtils;
import com.oner365.util.RequestUtils;
import com.oner365.util.RsaUtils;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;

/**
 * Controller Advice
 *
 * @author zhaoyong
 */
@ControllerAdvice(basePackages = "com.oner365")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResponseAdvice.class);

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ClientWhiteProperties clientWhiteProperties;

  @Override
  public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    return !returnType.getDeclaringClass().equals(Docket.class);
  }

  @Override
  public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType,
      @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {

    HttpServletRequest httpRequest = RequestUtils.getHttpRequest();
    // 加密对象返回
    if (RequestUtils.validateClientWhites(httpRequest.getRequestURI(), clientWhiteProperties.getWhites())) {
      String sign = httpRequest.getHeader("sign");
      if (DataUtils.isEmpty(sign)) {
        return ResponseData.error("加密串验证错误");
      }
      String key = RsaUtils.buildRsaDecryptByPrivateKey(sign, clientWhiteProperties.getPrivateKey());
      if (DataUtils.isEmpty(key)) {
        return ResponseData.error("解密失败");
      }
      if (body instanceof ResponseData) {
        return ResponseData.success(Base64.getEncoder()
            .encodeToString(Cipher.encodeSms4(JSON.toJSONString(body), key.substring(0, 16).getBytes())));
      }
      if (body instanceof byte[]) {
        return Base64.getEncoder().encodeToString(Cipher.encodeSms4((byte[]) body, key.substring(0, 16).getBytes()))
            .getBytes();
      }
      return ResponseData.success(
          Base64.getEncoder().encodeToString(Cipher.encodeSms4(body.toString(), key.substring(0, 16).getBytes())));
    }
    // 默认返回
    if (body instanceof String) {
      try {
        return objectMapper.writeValueAsString(ResponseData.success(String.valueOf(body)));
      } catch (JsonProcessingException e) {
        LOGGER.error("beforeBodyWrite error:", e);
      }
    }
    if (body instanceof byte[] || body instanceof ResponseData || isSwaggerResource(body)) {
      return body;
    }
    return ResponseData.success((Serializable) body);
  }

  private boolean isSwaggerResource(Object body) {
    if (body instanceof springfox.documentation.spring.web.json.Json) {
      return true;
    }
    if (body instanceof Collection) {
      Collection<?> collection = (Collection<?>) body;
      Iterator<?> iterator = collection.iterator();
      return iterator.hasNext() && iterator.next() instanceof SwaggerResource;
    }
    return false;
  }

}
