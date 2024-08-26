package com.oner365.sys.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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
    @Schema(name = "账号id", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.reset.userId.message}")
    private String userId;

    /**
     * 密码 password
     */
    @Schema(name = "密码", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.reset.password.message}")
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
