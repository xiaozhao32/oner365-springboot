package com.oner365.monitor.constants;

/**
 * 任务调度通用常量
 * 
 * @author zhaoyong
 */
public class ScheduleConstants {
    
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /** 执行目标key */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /** 默认 */
    public static final String MISFIRE_DEFAULT = "0";

    /** 立即触发执行 */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /** 触发一次执行 */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /** 不触发立即执行 */
    public static final String MISFIRE_DO_NOTHING = "3";
    
    /**
     * 任务服务名称
     */
    public static final String SCHEDULE_SERVER_NAME = "oner365-monitor";
    
    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "scheduleTask";
    
    /**
     * 队列类型
     */
    public static final String SCHEDULE_CONCURRENT = "1";

    private ScheduleConstants() {
        super();
    }
    
}
