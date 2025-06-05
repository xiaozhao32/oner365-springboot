package com.oner365.log.event;

import com.oner365.sys.vo.SysLogVo;

/**
 * SysLog Event
 *
 * @author zhaoyong
 *
 */
public class SysLogEvent {

    private final SysLogVo sysLog;

    /**
     * Constructor
     * @param sysLog SysLog
     */
    public SysLogEvent(SysLogVo sysLog) {
        super();
        this.sysLog = sysLog;
    }

    /**
     * @return the sysLog
     */
    public SysLogVo getSysLog() {
        return sysLog;
    }

}
