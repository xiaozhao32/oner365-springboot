package com.oner365.queue.constants;

import com.oner365.common.constants.PublicConstants;

/**
 * pulsar 常量设置
 * 
 * @author zhaoyong
 */
public class PulsarConstants extends PublicConstants {

    /**
     * message队列
     */
    public static final String MESSAGE_QUEUE_NAME = NAME + "." + "example";
    
    /**
     * route队列
     */
    public static final String ROUTE_QUEUE_NAME = NAME + "." + "syncRoute";
    public static final String ROUTE_QUEUE_TYPE = ROUTE_QUEUE_NAME + "." + MQ_FANOUT;
    public static final String ROUTE_QUEUE_KEY = ROUTE_QUEUE_NAME + "." + MQ_KEY;
    
    /**
     * 定时任务队列
     */
    public static final String SCHEDULE_TASK_QUEUE_NAME = NAME + "." + "scheduleTask";
    public static final String SCHEDULE_TASK_QUEUE_TYPE = SCHEDULE_TASK_QUEUE_NAME + "." + MQ_FANOUT;
    public static final String SCHEDULE_TASK_QUEUE_KEY = SCHEDULE_TASK_QUEUE_NAME + "." + MQ_KEY;

    /**
     * 更新执行状态队列
     */
    public static final String TASK_UPDATE_STATUS_QUEUE_NAME = NAME + "." + "updateExecuteStatusTask";
    public static final String TASK_UPDATE_STATUS_QUEUE_TYPE = TASK_UPDATE_STATUS_QUEUE_NAME + "." + MQ_FANOUT;
    public static final String TASK_UPDATE_STATUS_QUEUE_KEY = TASK_UPDATE_STATUS_QUEUE_NAME + "." + MQ_KEY;

    /**
     * 保存日志队列
     */
    public static final String SAVE_TASK_LOG_QUEUE_NAME = NAME + "." + "saveTaskLogTask";
    public static final String SAVE_TASK_LOG_QUEUE_TYPE = SAVE_TASK_LOG_QUEUE_NAME + "." + MQ_FANOUT;
    public static final String SAVE_TASK_LOG_QUEUE_KEY = SAVE_TASK_LOG_QUEUE_NAME + "." + MQ_KEY;
    
    private PulsarConstants() {
    }

}
