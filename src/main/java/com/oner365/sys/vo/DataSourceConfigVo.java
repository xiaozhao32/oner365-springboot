package com.oner365.sys.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.google.common.base.MoreObjects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据源配置 DataSourceConfig
 * 
 * @author zhaoyong
 */
@ApiModel(value = "数据源配置")
public class DataSourceConfigVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 连接名
     */
    @ApiModelProperty(value = "连接名称", required = true)
    @NotBlank(message = "数据源连接名称不能为空")
    private String connectName;

    /**
     * 数据库类型：mysql、oracle
     */
    @ApiModelProperty(value = "数据库类型")
    private String dbType;

    /**
     * 数据源类型: ds, cache
     */
    @ApiModelProperty(value = "数据源类型")
    private String dsType;

    /**
     * 数据库连接地址
     */
    @ApiModelProperty(value = "数据库ip")
    private String ip;

    /**
     * 驱动名称
     */
    @ApiModelProperty(value = "驱动名称")
    private String driverName;

    /**
     * 地址
     */
    @ApiModelProperty(value = "数据库地址")
    private String url;

    /**
     * 数据库名
     */
    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    /**
     * 端口
     */
    @ApiModelProperty(value = "端口")
    private int port;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "账号")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * Constructor
     */
    public DataSourceConfigVo() {
        super();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the connectName
     */
    public String getConnectName() {
        return connectName;
    }

    /**
     * @param connectName the connectName to set
     */
    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }

    /**
     * @return the dbType
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * @param dbType the dbType to set
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * @return the dsType
     */
    public String getDsType() {
        return dsType;
    }

    /**
     * @param dsType the dsType to set
     */
    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the driverName
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * @param driverName the driverName to set
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}
