package com.oner365.common.advice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.oner365.common.config.properties.ClientWhiteProperties;
import com.oner365.util.Cipher;
import com.oner365.util.RequestUtils;
import com.oner365.util.RsaUtils;

/**
 * Controller Advice
 *
 * @author liutao
 */
@ControllerAdvice(basePackages = "com.oner365")
public class RequestAdvice extends RequestBodyAdviceAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestAdvice.class);

  @Autowired
  private ClientWhiteProperties clientWhiteProperties;

  @Override
  public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type targetType,
                          @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @NotNull
  @Override
  public HttpInputMessage beforeBodyRead(@NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType,
                                         @NonNull Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
    HttpServletRequest request = RequestUtils.getHttpRequest();
    try {
      if (RequestUtils.validateClientWhites(request.getRequestURI(), clientWhiteProperties.getWhites())) {
        String body = RequestUtils.getRequestBody(inputMessage.getBody());
        String sign = Objects.requireNonNull(inputMessage.getHeaders().get("sign")).get(0);
        String key = RsaUtils.buildRsaDecryptByPrivateKey(sign, clientWhiteProperties.getPrivateKey());
        if (key != null) {
          String b = Cipher.decodeSms4toString(Base64.getDecoder().decode(body), key.substring(0, 16).getBytes());
          return new MappingJacksonInputMessage(new ByteArrayInputStream(b.getBytes()), inputMessage.getHeaders());
        }
      }
    } catch (Exception e) {
      LOGGER.error("beforeBodyRead ERROR:", e);
    }
    return new MappingJacksonInputMessage(inputMessage.getBody(), inputMessage.getHeaders());
  }

}
