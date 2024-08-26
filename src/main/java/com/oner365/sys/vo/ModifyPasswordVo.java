package com.oner365.sys.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;

/**
 * 修改密码对象
 *
 * @author zhaoyong
 *
 */
@Schema(name = "修改密码对象")
public class ModifyPasswordVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 旧密码 oldPassword
     */
    @Schema(name = "旧密码", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.modify.oldPassword.message}")
    private String oldPassword;

    /**
     * 新密码 password
     */
    @Schema(name = "新密码", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.modify.newPassword.message}")
    private String password;

    /**
     * 构造方法
     */
    public ModifyPasswordVo() {
        super();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
