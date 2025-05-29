package com.oner365.log.util;

import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.sys.vo.SysLogVo;

/**
 * SysLog对象
 *
 * @author zhaoyong
 *
 */
public final class SysLogUtils {

    private SysLogUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * SysLog对象
     * @return SysLog
     */
    public static SysLogVo getSysLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
            .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLogVo sysLog = new SysLogVo();
        sysLog.setOperationIp(HttpClientUtils.getIpAddress(request));
        sysLog.setOperationPath(request.getRequestURI());
        sysLog.setMethodName(request.getMethod());
        return sysLog;
    }

}
