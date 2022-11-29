package com.oner365.sys.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 菜单操作权限对象
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_menu_oper")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMenuOper implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "uuid")
  private String id;

  @Column(name = "menu_id", nullable = false, length = 32)
  private String menuId;

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "operation_id")
  private SysMenuOperation sysMenuOperation;

  /**
   * Constructor
   */
  public SysMenuOper() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMenuId() {
    return menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public SysMenuOperation getSysMenuOperation() {
    return sysMenuOperation;
  }

  public void setSysMenuOperation(SysMenuOperation sysMenuOperation) {
    this.sysMenuOperation = sysMenuOperation;
  }

}
