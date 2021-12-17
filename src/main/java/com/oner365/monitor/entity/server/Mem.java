package com.oner365.monitor.entity.server;

import java.io.Serializable;

import com.oner365.common.constants.PublicConstants;
import com.oner365.util.Arith;

/**
 * 內存相关信息
 * 
 * @author zhaoyong
 */
public class Mem implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 1L;
  
  /**
   * 内存总量
   */
  private double total;

  /**
   * 已用内存
   */
  private double used;

  /**
   * 剩余内存
   */
  private double free;

  public double getTotal() {
    return Arith.div(total, (PublicConstants.BYTE_SIZE * PublicConstants.BYTE_SIZE * PublicConstants.BYTE_SIZE), 2);
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public double getUsed() {
    return Arith.div(used, (PublicConstants.BYTE_SIZE * PublicConstants.BYTE_SIZE * PublicConstants.BYTE_SIZE), 2);
  }

  public void setUsed(long used) {
    this.used = used;
  }

  public double getFree() {
    return Arith.div(free, (PublicConstants.BYTE_SIZE * PublicConstants.BYTE_SIZE * PublicConstants.BYTE_SIZE), 2);
  }

  public void setFree(long free) {
    this.free = free;
  }

  public double getUsage() {
    return Arith.mul(Arith.div(used, total, 4), 100);
  }
}
