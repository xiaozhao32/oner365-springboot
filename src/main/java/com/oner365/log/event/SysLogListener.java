package com.oner365.log.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import com.oner365.sys.service.ISysLogService;

/**
 * SysLog Listener
 *
 * @author zhaoyong
 *
 */
public class SysLogListener {

    private final ISysLogService sysLogService;

    /**
     * Constructor
     *
     * @param sysLogService ISysLogService
     */
    public SysLogListener(ISysLogService sysLogService) {
        super();
        this.sysLogService = sysLogService;
    }

    /**
     * save sysLog
     *
     * @param event SysLogEvent
     */
    @Async
    @Order
    @EventListener({ SysLogEvent.class })
    public void saveSysLog(SysLogEvent event) {
        this.sysLogService.save(event.getSysLog());
    }
}
