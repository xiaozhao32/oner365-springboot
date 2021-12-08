package com.oner365.log.aspect;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpMethod;

import com.alibaba.fastjson.JSON;
import com.oner365.log.event.SysLogEvent;
import com.oner365.log.util.SysLogUtils;
import com.oner365.sys.vo.SysLogVo;
import com.oner365.util.DataUtils;

/**
 * SysLog Aspect日志拦截器 使用时@sysLog("名称")
 *
 * @author zhaoyong
 *
 */
@Aspect
public class SysLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogAspect.class);

    private final ApplicationEventPublisher publisher;

    /**
     * Constructor
     * @param publisher ApplicationEventPublisher
     */
    public SysLogAspect(ApplicationEventPublisher publisher) {
        super();
        this.publisher = publisher;
    }

    /**
     * Aspect around
     * @param point ProceedingJoinPoint
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
            entity.setCreateTime(LocalDateTime.now());
            Object obj = point.proceed();
            this.publisher.publishEvent(new SysLogEvent(entity));
            return obj;
        } catch (Throwable e) {
            LOGGER.error("SysLogAspect error:", e);
        }
        return null;
    }

    private String getParams(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (!DataUtils.isEmpty(paramsArray)) {
            for (Object o : paramsArray) {
                if (!DataUtils.isEmpty(o)) {
                    Object jsonObj = JSON.toJSON(o);
                    if (!DataUtils.isEmpty(jsonObj)) {
                        params.append(jsonObj.toString()).append(" ");
                    }
                }
            }
        }
        return params.toString().trim();
    }
}
