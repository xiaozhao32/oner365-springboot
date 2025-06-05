package com.oner365.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.oner365.log.aspect.SysLogAspect;
import com.oner365.log.event.SysLogListener;
import com.oner365.sys.service.ISysLogService;

/**
 * SysLog Configuration
 *
 * @author zhaoyong
 *
 */
@EnableAsync
@Configuration
@ConditionalOnWebApplication
public class LogAutoConfiguration {

    private final ISysLogService sysLogService;

    /**
     * Constructor
     * @param sysLogService ISysLogService
     */
    public LogAutoConfiguration(ISysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * new SysLogListener
     * @return SysLogListener
     */
    @Bean
    SysLogListener sysLogListener() {
        return new SysLogListener(this.sysLogService);
    }

    /**
     * new SysLogAspect
     * @param publisher ApplicationEventPublisher
     * @return SysLogAspect
     */
    @Bean
    SysLogAspect sysLogAspect(ApplicationEventPublisher publisher) {
        return new SysLogAspect(publisher);
    }

}
