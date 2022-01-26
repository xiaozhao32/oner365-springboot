package com.oner365.common.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 消息类型
 *
 * @author zhaoyong
 */
public enum MessageStatusEnum implements Serializable {

    /** 已读 */
    READ("1", "已读"),
    /** 未读 */
    READ_NONE("0", "未读");

    /**
     * 编码
     */
    private final String code;

    /**
     * 名称
     */
    private final String name;

    /**
     * 构造方法
     *
     * @param code  编码
     * @param name 名称
     */
    MessageStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * get code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 获取枚举
     *
     * @param code 编码
     * @return StatusEnum
     */
    public static MessageStatusEnum getCode(String code) {
        Optional<MessageStatusEnum> result = Arrays.stream(MessageStatusEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}
