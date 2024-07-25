package com.oner365.log.aspect;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpMethod;

import com.alibaba.fastjson.JSON;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.log.event.SysLogEvent;
import com.oner365.log.util.SysLogUtils;
import com.oner365.sys.vo.SysLogVo;

/**
 * SysLog Aspect日志拦截器 使用时@sysLog("名称")
 *
 * @author zhaoyong
 */
@Aspect
public class SysLogAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(SysLogAspect.class);

  private final ApplicationEventPublisher publisher;

  /**
   * Constructor
   *
   * @param publisher ApplicationEventPublisher
   */
  public SysLogAspect(ApplicationEventPublisher publisher) {
    super();
    this.publisher = publisher;
  }

  /**
   * Aspect around
   *
   * @param point  ProceedingJoinPoint
   * @param sysLog SysLog
   * @return Object
   */
  @Around("@annotation(sysLog)")
  public Object around(ProceedingJoinPoint point, com.oner365.log.annotation.SysLog sysLog) {
    try {
      String className = point.getTarget().getClass().getName();
      String methodName = point.getSignature().getName();
      LOGGER.debug("[Class]:{},[Method]:{}", className, methodName);
      
      SysLogVo entity = SysLogUtils.getSysLog();
      entity.setOperationName(sysLog.value());
      if (HttpMethod.PUT.name().equals(methodName) || HttpMethod.POST.name().equals(methodName)) {
        String params = getParams(point.getArgs());
        entity.setOperationContext(StringUtils.substring(params, 0, 2000));
      }
      this.publisher.publishEvent(new SysLogEvent(entity));
      return point.proceed();
    } catch (Throwable e) {
      LOGGER.error("SysLogAspect error:", e);
    }
    return null;
  }

  private String getParams(Object[] paramsArray) {
    String params = "";
    if (!DataUtils.isEmpty(paramsArray)) {
      params = Arrays.stream(paramsArray).filter(o -> !DataUtils.isEmpty(o))
              .map(JSON::toJSON).filter(jsonObj -> !DataUtils.isEmpty(jsonObj))
              .map(jsonObj -> jsonObj.toString() + " ").collect(Collectors.joining());
    }
    return params.trim();
  }
}
