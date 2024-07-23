package com.oner365.sys.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 菜单操作对象
 * 
 * @author zhaoyong
 *
 */
@Schema(name = "菜单操作对象")
public class SysMenuOperDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 操作id
   */
  @Schema(name = "操作id")
  private String operId;
  
  /**
   * 操作名称
   */
  @Schema(name = "操作名称")
  private String operName;
  
  /**
   * 操作类型
   */
  @Schema(name = "操作类型")
  private String operType;
  
  public SysMenuOperDto() {
    super();
  }

  public String getOperId() {
    return operId;
  }

  public void setOperId(String operId) {
    this.operId = operId;
  }

  public String getOperName() {
    return operName;
  }

  public void setOperName(String operName) {
    this.operName = operName;
  }

  public String getOperType() {
    return operType;
  }

  public void setOperType(String operType) {
    this.operType = operType;
  }
  
}
