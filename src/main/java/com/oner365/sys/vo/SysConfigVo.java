package com.oner365.sys.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.oner365.common.enums.StatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * nt_sys_config 对象 nt_sys_config
 * 
 * @author zhaoyong
 */
@ApiModel(value = "nt_sys_config")
public class SysConfigVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键 */
  @ApiModelProperty(value = "主键")
  private String id;

  /** 配置名称 */
  @ApiModelProperty(value = "配置名称", required = true)
  private String configName;

  /** 配置内容 */
  @ApiModelProperty(value = "配置内容", required = false)
  private String configValue;

  /** 状态 */
  @ApiModelProperty(value = "状态", required = true)
  private StatusEnum status;

  /** 创建时间 */
  @ApiModelProperty(value = "创建时间", required = true)
  private LocalDateTime createTime;

  /** 更新时间 */
  @ApiModelProperty(value = "更新时间", required = false)
  private LocalDateTime updateTime;

  /** 创建人 */
  @ApiModelProperty(value = "创建人")
  private String createUser;

  /** 修改人 */
  @ApiModelProperty(value = "修改人")
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
