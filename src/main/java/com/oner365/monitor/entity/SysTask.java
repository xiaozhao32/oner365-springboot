package com.oner365.monitor.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.util.CronUtils;
import com.oner365.util.DataUtils;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

/**
 * 定时任务调度表 nt_sys_task
 *
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_task")
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysTask implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "uuid")
  private String id;

  /**
   * 任务名称
   */
  @Column(name = "task_name", nullable = false, length = 64)
  private String taskName;

  /**
   * 任务组名
   */
  @Column(name = "task_group", nullable = false, length = 64)
  private String taskGroup;

  /**
   * 调用目标字符串
   */
  @Column(name = "invoke_target", nullable = false, length = 500)
  private String invokeTarget;

  /**
   * 调用目标参数
   */
  @Type(type = "json")
  @Column(name = "invoke_param", columnDefinition = "json")
  private InvokeParam invokeParam;

  /**
   * cron执行表达式
   */
  @Column(name = "cron_expression")
  private String cronExpression;

  /**
   * cron计划策略
   */
  @Column(name = "misfire_policy", length = 20)
  private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

  /**
   * 是否并发执行（0允许 1禁止）
   */
  @Column(name = "concurrent", length = 1)
  private String concurrent;

  /**
   * 任务状态（0正常 1暂停）
   */
  @Column(name = "status", length = 1)
  private String status;

  /**
   * 执行任务状态（0正在执行 1执行完成）
   */
  @Column(name = "execute_status", length = 1)
  private String executeStatus;

  /**
   * 备注
   */
  @Column(name = "remark", length = 500)
  private String remark;

  /**
   * 创建人
   */
  @Column(name = "create_user", length = 32)
  private String createUser;

  /**
   * 创建时间
   */
  @Column(name = "create_time", updatable = false)
  private Date createTime;

  /**
   * 更新时间
   */
  @Column(name = "update_time", insertable = false)
  private Date updateTime;

  public SysTask() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @NotBlank(message = "任务名称不能为空")
  @Size(max = 64, message = "任务名称不能超过64个字符")
  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskGroup() {
    return taskGroup;
  }

  public void setTaskGroup(String taskGroup) {
    this.taskGroup = taskGroup;
  }

  @NotBlank(message = "调用目标字符串不能为空")
  @Size(max = 1000, message = "调用目标字符串长度不能超过500个字符")
  public String getInvokeTarget() {
    return invokeTarget;
  }

  public void setInvokeTarget(String invokeTarget) {
    this.invokeTarget = invokeTarget;
  }

  @NotBlank(message = "Cron执行表达式不能为空")
  @Size(max = 255, message = "Cron执行表达式不能超过255个字符")
  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date getNextValidTime() {
    if (!DataUtils.isEmpty(cronExpression)) {
      return CronUtils.getNextExecution(cronExpression);
    }
    return null;
  }

  public String getMisfirePolicy() {
    return misfirePolicy;
  }

  public void setMisfirePolicy(String misfirePolicy) {
    this.misfirePolicy = misfirePolicy;
  }

  public String getConcurrent() {
    return concurrent;
  }

  public void setConcurrent(String concurrent) {
    this.concurrent = concurrent;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public InvokeParam getInvokeParam() {
    return invokeParam;
  }

  public void setInvokeParam(InvokeParam invokeParam) {
    this.invokeParam = invokeParam;
  }

  public String getExecuteStatus() {
    return executeStatus;
  }

  public void setExecuteStatus(String executeStatus) {
    this.executeStatus = executeStatus;
  }

  /**
   * 转换对象
   * 
   * @return SysTaskDto
   */
  public SysTaskDto toDto() {
    SysTaskDto result = new SysTaskDto();
    result.setId(this.getId());
    result.setConcurrent(this.getConcurrent());
    result.setCreateTime(this.getCreateTime());
    result.setCreateUser(this.getCreateUser());
    result.setCronExpression(this.getCronExpression());
    result.setExecuteStatus(this.getExecuteStatus());
    result.setInvokeParam(this.getInvokeParam());
    result.setInvokeTarget(this.getInvokeTarget());
    result.setMisfirePolicy(this.getMisfirePolicy());
    result.setRemark(this.getRemark());
    result.setStatus(this.getStatus());
    result.setTaskGroup(this.getTaskGroup());
    result.setTaskName(this.getTaskName());
    result.setUpdateTime(this.getUpdateTime());
    return result;
  }

}
