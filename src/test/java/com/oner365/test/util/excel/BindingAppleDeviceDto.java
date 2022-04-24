package com.oner365.test.util.excel;

import java.io.Serializable;
/**
* 读取excel文件数据传输类
* 
* @author LT
*
*/
public class BindingAppleDeviceDto implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
  * 资产编号 assetsNo
  */
  private String assetsNo;

  /**
  * 用户名称 userName
  */
  private String userName;
  
  /**
  * 序列号 serialNumber
  */
  private String serialNumber;
  
  /**
  * 部门 department
  */
  private String department;
  
  /**
  * 工号 jobNumber
  */
  private String jobNumber;
  
  /**
  * 电话 phone
  */
  private String phone;
  
  public BindingAppleDeviceDto() {
    
  }

  public String getAssetsNo() {
    return assetsNo;
  }

  public void setAssetsNo(String assetsNo) {
    this.assetsNo = assetsNo;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getJobNumber() {
    return jobNumber;
  }

  public void setJobNumber(String jobNumber) {
    this.jobNumber = jobNumber;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  
	  
}
