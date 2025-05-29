package com.oner365.sys.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 登录数据对象
 *
 * @author zhaoyong
 *
 */
@Schema(name = "登录数据对象")
public class LoginUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    @Schema(name = "token")
    private String accessToken;

    /**
     * token有效期
     */
    @Schema(name = "token有效期")
    private Long expireTime;

    /**
     * 账号id
     */
    @Schema(name = "账号id")
    private String userId;

    /**
     * 真实姓名
     */
    @Schema(name = "真实姓名")
    private String realName;

    /**
     * 是否管理员
     */
    @Schema(name = "是否管理员")
    private String isAdmin;

    /**
     * 头像
     */
    @Schema(name = "头像")
    private String avatar;

    /**
     * 角色信息
     */
    @Schema(name = "角色信息")
    private List<String> roles = new ArrayList<>();

    /**
     * 职位信息
     */
    @Schema(name = "职位信息")
    private List<String> jobs = new ArrayList<>();

    /**
     * 机构信息
     */
    @Schema(name = "机构信息")
    private List<String> orgs = new ArrayList<>();

    /**
     * 构造方法
     */
    public LoginUserDto() {
        super();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public List<String> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

}
