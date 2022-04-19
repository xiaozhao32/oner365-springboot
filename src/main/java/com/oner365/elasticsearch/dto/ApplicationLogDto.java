package com.oner365.elasticsearch.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Application Log
 *
 * @author zhaoyong
 */
@ApiModel(value = "应用日志")
public class ApplicationLogDto implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @ApiModelProperty(value = "主键")
  private String id;

  /**
   * 线程名称
   */
  @ApiModelProperty(value = "线程名称")
  private String threadName;
  
  /**
   * 版本
   */
  @ApiModelProperty(value = "版本")
  private String version;
  
  /**
   * 消息内容
   */
  @ApiModelProperty(value = "消息内容")
  private String message;
  
  /**
   * 日志级别
   */
  @ApiModelProperty(value = "日志级别")
  private String level;
  
  /**
   * 类名称
   */
  @ApiModelProperty(value = "类名称")
  private String loggerName;
  
  /**
   * 创建时间
   */
  @ApiModelProperty(value = "创建时间")
  private String createTime;
  
  /**
   * 构造方法
   */
  public ApplicationLogDto() {
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
   * @return the threadName
   */
  public String getThreadName() {
    return threadName;
  }

  /**
   * @param threadName the threadName to set
   */
  public void setThreadName(String threadName) {
    this.threadName = threadName;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the level
   */
  public String getLevel() {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(String level) {
    this.level = level;
  }

  /**
   * @return the loggerName
   */
  public String getLoggerName() {
    return loggerName;
  }

  /**
   * @param loggerName the loggerName to set
   */
  public void setLoggerName(String loggerName) {
    this.loggerName = loggerName;
  }

  /**
   * @return the createTime
   */
  public String getCreateTime() {
    return createTime;
  }

  /**
   * @param createTime the createTime to set
   */
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}