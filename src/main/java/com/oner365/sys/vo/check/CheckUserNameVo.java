package com.oner365.sys.vo.check;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;

/**
 * 检测用户编码
 *
 * @author zhaoyong
 *
 */
@Schema(name = "检测用户编码")
public class CheckUserNameVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @Schema(name = "主键")
    private String id;

    /**
     * 用户名称
     */
    @Schema(name = "用户名称", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.check.userName.message}")
    private String userName;

    /**
     * 构造方法
     */
    public CheckUserNameVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
