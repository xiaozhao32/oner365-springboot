package com.oner365.sys.vo;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 重置密码对象
 *
 * @author zhaoyong
 *
 */
@ApiModel(value = "重置密码对象")
public class ResetPasswordVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 账号 userId
     */
    @ApiModelProperty(value = "账号id", required = true)
    @NotBlank(message = "账号id不能为空")
    private String userId;

    /**
     * 密码 password
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "重置密码不能为空")
    private String password;

    /**
     * 构造方法
     */
    public ResetPasswordVo() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
