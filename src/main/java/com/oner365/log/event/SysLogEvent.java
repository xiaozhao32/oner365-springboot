package com.oner365.log.event;

import com.oner365.sys.entity.SysLog;

/**
 * SysLog Event
 *
 * @author zhaoyong
 *
 */
public class SysLogEvent {

    private final SysLog sysLog;

    /**
     * Constructor
     *
     * @param sysLog SysLog
     */
    public SysLogEvent(SysLog sysLog) {
        super();
        this.sysLog = sysLog;
    }

    /**
     * @return the sysLog
     */
    public SysLog getSysLog() {
        return sysLog;
    }
}
