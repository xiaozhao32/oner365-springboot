package com.oner365.gateway.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oner365.common.ResponseData;
import com.oner365.gateway.entity.GatewayError;

/**
 * 异常处理
 * 
 * @author zhaoyong
 *
 */
@ResponseBody
@ControllerAdvice
public class GatewayErrorWebExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GatewayErrorWebExceptionHandler.class);

  private static final String ERROR_MESSAGE = "服务器异常，请联系管理员!";

  /**
   * 构造方法
   */
  public GatewayErrorWebExceptionHandler() {
    super();
  }

  /**
   * 异常处理
   * 
   * @param request 请求对象
   * @param e       异常
   * @return ResponseData
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseData<GatewayError> exceptionHandler(HttpServletRequest request, Exception e) {
    LOGGER.error("[网关异常] 请求路径:{}, 异常信息:{}", request.getRequestURI(), e.getMessage());
    GatewayError result = getErrorAttributes(request, e);
    return ResponseData.error(result, HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_MESSAGE);
  }

  private GatewayError getErrorAttributes(HttpServletRequest request, Exception error) {
    GatewayError result = new GatewayError();
    result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    result.setMethod(request.getMethod());
    result.setPath(request.getRequestURI());

    result.setMessage(ERROR_MESSAGE);
    result.setResult(error.getMessage());
    return result;
  }
}
