package com.oner365.queue.constants;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * 队列常量设置
 *
 * @author zhaoyong
 */
public class QueueConstants {

    /**
     * message队列
     */
    public static final String MESSAGE_QUEUE_NAME = PublicConstants.NAME + "." + "example";

    public static final String MESSAGE_QUEUE_TYPE = MESSAGE_QUEUE_NAME + "." + PublicConstants.MQ_DIRECT;

    public static final String MESSAGE_QUEUE_KEY = MESSAGE_QUEUE_NAME + "." + PublicConstants.MQ_KEY;

    /**
     * route队列
     */
    public static final String ROUTE_QUEUE_NAME = PublicConstants.NAME + "." + "syncRoute";

    public static final String ROUTE_QUEUE_TYPE = ROUTE_QUEUE_NAME + "." + PublicConstants.MQ_FANOUT;

    public static final String ROUTE_QUEUE_KEY = ROUTE_QUEUE_NAME + "." + PublicConstants.MQ_KEY;

    /**
     * 定时任务队列
     */
    public static final String SCHEDULE_TASK_QUEUE_NAME = PublicConstants.NAME + "." + "scheduleTask";

    public static final String SCHEDULE_TASK_QUEUE_TYPE = SCHEDULE_TASK_QUEUE_NAME + "." + PublicConstants.MQ_FANOUT;

    public static final String SCHEDULE_TASK_QUEUE_KEY = SCHEDULE_TASK_QUEUE_NAME + "." + PublicConstants.MQ_KEY;

    /**
     * 更新执行状态队列
     */
    public static final String TASK_UPDATE_STATUS_QUEUE_NAME = PublicConstants.NAME + "." + "updateExecuteStatusTask";

    public static final String TASK_UPDATE_STATUS_QUEUE_TYPE = TASK_UPDATE_STATUS_QUEUE_NAME + "."
            + PublicConstants.MQ_FANOUT;

    public static final String TASK_UPDATE_STATUS_QUEUE_KEY = TASK_UPDATE_STATUS_QUEUE_NAME + "."
            + PublicConstants.MQ_KEY;

    /**
     * 保存日志队列
     */
    public static final String SAVE_TASK_LOG_QUEUE_NAME = PublicConstants.NAME + "." + "saveTaskLogTask";

    public static final String SAVE_TASK_LOG_QUEUE_TYPE = SAVE_TASK_LOG_QUEUE_NAME + "." + PublicConstants.MQ_FANOUT;

    public static final String SAVE_TASK_LOG_QUEUE_KEY = SAVE_TASK_LOG_QUEUE_NAME + "." + PublicConstants.MQ_KEY;

    private QueueConstants() {
    }

}
