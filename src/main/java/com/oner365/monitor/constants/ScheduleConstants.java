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
