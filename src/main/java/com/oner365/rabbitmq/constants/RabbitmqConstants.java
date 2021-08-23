package com.oner365.rabbitmq.constants;

import com.oner365.common.constants.PublicConstants;

/**
 * rabbitmq 常量设置
 * 
 * @author zhaoyong
 */
public class RabbitmqConstants extends PublicConstants {

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = NAME + "." + "example";
    
    /**
     * 队列类型
     */
    public static final String QUEUE_TYPE = QUEUE_NAME + "." + "direct";
    
    /**
     * 队列标识
     */
    public static final String QUEUE_KEY = QUEUE_NAME + "." + "key";

    private RabbitmqConstants() {
    }

}
