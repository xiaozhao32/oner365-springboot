package com.oner365.sys.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 重置密码对象
 *
 * @author zhaoyong
 *
 */
@Schema(name = "重置密码对象")
public class ResetPasswordVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 账号 userId
     */
    @Schema(name = "账号id", required = true)
    @NotBlank(message = "账号id不能为空")
    private String userId;

    /**
     * 密码 password
     */
    @Schema(name = "密码", required = true)
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
