package com.oner365.sys.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.oner365.sys.enums.SysUserTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户Token
 *
 * @author zhaoyong
 *
 */
@Schema(description = "用户Token")
public class UserTokenDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * Token Type
     */
    @Schema(description = "类型")
    private String tokenType;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String userName;

    /**
     * 密码密文
     */
    @Schema(description = "密码密文")
    private String password;

    /**
     * 是否管理员
     */
    @Schema(description = "是否管理员")
    private String isAdmin;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private SysUserTypeEnum userType;

    /**
     * 角色
     */
    @Schema(description = "角色")
    private List<String> roles = new ArrayList<>();

    /**
     * 单位
     */
    @Schema(description = "单位")
    private List<String> orgs = new ArrayList<>();

    /**
     * 部门
     */
    @Schema(description = "部门")
    private List<String> jobs = new ArrayList<>();

    /**
     * 菜单类型
     */
    @Schema(description = "菜单类型")
    private String menuType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
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

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public SysUserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(SysUserTypeEnum userType) {
        this.userType = userType;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

}
