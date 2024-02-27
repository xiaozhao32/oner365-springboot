package com.oner365.api.dto;

import java.io.Serializable;

/**
 * Message
 *
 * @author zhaoyong
 */
public class Message implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * data
   */
  private String data;
  
  /**
   * total
   */
  private Integer total;

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }
}
