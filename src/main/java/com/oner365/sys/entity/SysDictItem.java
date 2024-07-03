package com.oner365.sys.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.enums.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 字典对象
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_dict_item")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysDictItem implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  /**
   * 类型编号
   */
  @Column(name = "dict_item_type_id", nullable = false, length = 32)
  private String typeId;

  /**
   * 字典编码
   */
  @Column(name = "dict_item_code", nullable = false, length = 32)
  private String itemCode;

  /**
   * 字典名称
   */
  @Column(name = "dict_item_name", length = 32)
  private String itemName;

  /**
   * 字典排序
   */
  @Column(name = "dict_item_order", length = 10)
  private Integer itemOrder;

  /**
   * 状态
   */
  @Enumerated
  @Column(name = "status", nullable = false)
  private StatusEnum status;

  /**
   * 父级id
   */
  @Column(name = "parent_id", length = 64)
  private String parentId;

  /**
   * Constructor
   */
  public SysDictItem() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTypeId() {
    return typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public Integer getItemOrder() {
    return itemOrder;
  }

  public void setItemOrder(Integer itemOrder) {
    this.itemOrder = itemOrder;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

}
