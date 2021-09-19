package com.oner365.common.query;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询属性配置
 * @author zhaoyong
 */
@ApiModel(value = "查询条件")
public class AttributeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 相等 EQ, 不相等 NE, 模糊 LIKE, 大于 GT, 小于 LT, 大于等于 GTE, 小于等于 LTE, 和 AND, 或 OR, 包含 IN, 之间 BE
     */
    @ApiModelProperty(value = "符号")
    private String opt;

    /**
     * 键
     */
    @ApiModelProperty(value = "键")
    private String key;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private String val;

    /**
     * 构造对象
     */
    public AttributeBean() {
        super();
    }

    public AttributeBean(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}
