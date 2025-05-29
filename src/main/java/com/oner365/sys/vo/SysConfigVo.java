package com.oner365.sys.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * nt_sys_config 对象 nt_sys_config
 *
 * @author zhaoyong
 */
@Schema(name = "nt_sys_config")
public class SysConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Schema(name = "主键")
    private String id;

    /** 配置名称 */
    @Schema(name = "配置名称", requiredMode = RequiredMode.REQUIRED)
    private String configName;

    /** 配置内容 */
    @Schema(name = "配置内容", requiredMode = RequiredMode.NOT_REQUIRED)
    private String configValue;

    /** 状态 */
    @Schema(name = "状态", requiredMode = RequiredMode.REQUIRED)
    private StatusEnum status;

    /** 创建时间 */
    @Schema(name = "创建时间", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    /** 更新时间 */
    @Schema(name = "更新时间", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime updateTime;

    /** 创建人 */
    @Schema(name = "创建人")
    private String createUser;

    /** 修改人 */
    @Schema(name = "修改人")
    private String updateUser;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
