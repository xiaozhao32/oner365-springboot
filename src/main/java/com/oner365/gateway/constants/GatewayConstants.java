package com.oner365.gateway.constants;

/**
 * 网关常量
 * @author zhaoyong
 */
public class GatewayConstants  {
    
    /**
     * 公共名称
     */
    public static final String NAME = "oner365";
    
    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = NAME + "." + "syncRoute";
    
    /**
     * 队列标识
     */
    public static final String QUEUE_KEY = QUEUE_NAME + "." + "key";
    
    /**
     * 队列类型
     */
    public static final String QUEUE_TYPE = NAME + "." + "fanout";
    

    public static final String PREDICATE_NAME = "Path";
    
    public static final String PREDICATE_ARGS_PATTERN = "pattern";
    
    public static final String ROUT_STATUS_DISABLE = "0";
    
    public static final String ERROR_MESSAGE_401 = "token 验证不正确!";
    public static final String ERROR_MESSAGE_404 = "请求地址不存在，请联系管理员!";
    public static final String ERROR_MESSAGE_500 = "服务器错误，请联系管理员!";
    public static final String ERROR_MESSAGE_503 = "服务未找到，请联系管理员!";
    
    /**
     * Constructor
     */
    private GatewayConstants() {
    }
}
