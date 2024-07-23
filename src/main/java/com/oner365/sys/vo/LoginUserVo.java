package com.oner365.sys.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 登录对象
 * 
 * @author zhaoyong
 *
 */
@Schema(name = "登录对象")
public class LoginUserVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 账号 userName
     */
    @Schema(name = "账号", required = true)
    @NotBlank(message = "登录账号不能为空")
    private String userName;
    
    /**
     * 密码 password
     */
    @Schema(name = "密码", required = true)
    @NotBlank(message = "登录密码不能为空")
    private String password;
    
    /**
     * 图片验证码 uuid
     */
    @Schema(name = "图片验证码")
    private String uuid;
    
    /**
     * 验证码 code
     */
    @Schema(name = "验证码")
    private String code;
    
    /**
     * 构造方法
     */
    public LoginUserVo() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
