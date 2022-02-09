package com.oner365.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统用户
 *
 * @author zhaoyong
 */
@ApiModel(value = "用户信息")
public class SysUserDto implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @ApiModelProperty(value = "主键")
  private String id;

  /**
   * 用户标识
   */
  @ApiModelProperty(value = "用户标识")
  private String userCode;

  /**
   * 账号
   */
  @ApiModelProperty(value = "账号", required = true)
  private String userName;

  /**
   * 密码
   */
  @ApiModelProperty(value = "密码", required = true)
  private String password;

  /**
   * 真实姓名
   */
  @ApiModelProperty(value = "真实姓名")
  private String realName;

  /**
   * 头像
   */
  @ApiModelProperty(value = "头像")
  private String avatar;

  /**
   * 性别
   */
  @ApiModelProperty(value = "性别")
  private String sex;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态")
  private String status;

  /**
   * 最后登录时间
   */
  @ApiModelProperty(value = "最后登录时间")
  private LocalDateTime lastTime;

  /**
   * 创建时间
   */
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  /**
   * 最后登录ip
   */
  @ApiModelProperty(value = "最后登录ip")
  private String lastIp;

  /**
   * 邮箱
   */
  @ApiModelProperty(value = "邮箱")
  private String email;

  /**
   * 电话
   */
  @ApiModelProperty(value = "电话")
  private String phone;

  /**
   * 身份证
   */
  @ApiModelProperty(value = "身份证")
  private String idCard;

  /**
   * 是否管理员
   */
  @ApiModelProperty(value = "是否管理员")
  private String isAdmin;

  /**
   * 默认密码
   */
  @ApiModelProperty(value = "默认密码")
  private String defaultPassword;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态")
  private String activeStatus;

  /**
   * 用户类型
   */
  @ApiModelProperty(value = "用户类型")
  private String userType;

  /**
   * 证件类型
   */
  @ApiModelProperty(value = "证件类型")
  private String idType;

  /**
   * 备注
   */
  @ApiModelProperty(value = "备注")
  private String remark;

  /**
   * 角色ID
   */
  @ApiModelProperty(value = "角色ID列表")
  private List<String> roles = new ArrayList<>();

  /**
   * 角色名称列表
   */
  @ApiModelProperty(value = "角色名称列表")
  private List<String> roleNameList = new ArrayList<>();

  /**
   * 职位ID
   */
  @ApiModelProperty(value = "职位ID列表")
  private List<String> jobs = new ArrayList<>();

  /**
   * 职位名称列表
   */
  @ApiModelProperty(value = "职位名称列表")
  private List<String> jobNameList = new ArrayList<>();

  /**
   * 机构ID
   */
  @ApiModelProperty(value = "机构ID列表")
  private List<String> orgs = new ArrayList<>();

  /**
   * 机构名称列表
   */
  @ApiModelProperty(value = "机构名称列表")
  private List<String> orgNameList = new ArrayList<>();

  /**
   * 构造方法
   */
  public SysUserDto() {
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
   * @return the userCode
   */
  public String getUserCode() {
    return userCode;
  }

  /**
   * @param userCode the userCode to set
   */
  public void setUserCode(String userCode) {
    this.userCode = userCode;
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
   * @return the realName
   */
  public String getRealName() {
    return realName;
  }

  /**
   * @param realName the realName to set
   */
  public void setRealName(String realName) {
    this.realName = realName;
  }

  /**
   * @return the avatar
   */
  public String getAvatar() {
    return avatar;
  }

  /**
   * @param avatar the avatar to set
   */
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  /**
   * @return the sex
   */
  public String getSex() {
    return sex;
  }

  /**
   * @param sex the sex to set
   */
  public void setSex(String sex) {
    this.sex = sex;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the lastTime
   */
  public LocalDateTime getLastTime() {
    return lastTime;
  }

  /**
   * @param lastTime the lastTime to set
   */
  public void setLastTime(LocalDateTime lastTime) {
    this.lastTime = lastTime;
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
   * @return the lastIp
   */
  public String getLastIp() {
    return lastIp;
  }

  /**
   * @param lastIp the lastIp to set
   */
  public void setLastIp(String lastIp) {
    this.lastIp = lastIp;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the idCard
   */
  public String getIdCard() {
    return idCard;
  }

  /**
   * @param idCard the idCard to set
   */
  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  /**
   * @return the isAdmin
   */
  public String getIsAdmin() {
    return isAdmin;
  }

  /**
   * @param isAdmin the isAdmin to set
   */
  public void setIsAdmin(String isAdmin) {
    this.isAdmin = isAdmin;
  }

  /**
   * @return the defaultPassword
   */
  public String getDefaultPassword() {
    return defaultPassword;
  }

  /**
   * @param defaultPassword the defaultPassword to set
   */
  public void setDefaultPassword(String defaultPassword) {
    this.defaultPassword = defaultPassword;
  }

  /**
   * @return the activeStatus
   */
  public String getActiveStatus() {
    return activeStatus;
  }

  /**
   * @param activeStatus the activeStatus to set
   */
  public void setActiveStatus(String activeStatus) {
    this.activeStatus = activeStatus;
  }

  /**
   * @return the userType
   */
  public String getUserType() {
    return userType;
  }

  /**
   * @param userType the userType to set
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

  /**
   * @return the idType
   */
  public String getIdType() {
    return idType;
  }

  /**
   * @param idType the idType to set
   */
  public void setIdType(String idType) {
    this.idType = idType;
  }

  /**
   * @return the remark
   */
  public String getRemark() {
    return remark;
  }

  /**
   * @param remark the remark to set
   */
  public void setRemark(String remark) {
    this.remark = remark;
  }

  /**
   * @return the roles
   */
  public List<String> getRoles() {
    return roles;
  }

  /**
   * @param roles the roles to set
   */
  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  /**
   * @return the roleNameList
   */
  public List<String> getRoleNameList() {
    return roleNameList;
  }

  /**
   * @param roleNameList the roleNameList to set
   */
  public void setRoleNameList(List<String> roleNameList) {
    this.roleNameList = roleNameList;
  }

  /**
   * @return the jobs
   */
  public List<String> getJobs() {
    return jobs;
  }

  /**
   * @param jobs the jobs to set
   */
  public void setJobs(List<String> jobs) {
    this.jobs = jobs;
  }

  /**
   * @return the jobNameList
   */
  public List<String> getJobNameList() {
    return jobNameList;
  }

  /**
   * @param jobNameList the jobNameList to set
   */
  public void setJobNameList(List<String> jobNameList) {
    this.jobNameList = jobNameList;
  }

  /**
   * @return the orgs
   */
  public List<String> getOrgs() {
    return orgs;
  }

  /**
   * @param orgs the orgs to set
   */
  public void setOrgs(List<String> orgs) {
    this.orgs = orgs;
  }

  /**
   * @return the orgNameList
   */
  public List<String> getOrgNameList() {
    return orgNameList;
  }

  /**
   * @param orgNameList the orgNameList to set
   */
  public void setOrgNameList(List<String> orgNameList) {
    this.orgNameList = orgNameList;
  }

}
