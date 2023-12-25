package com.oner365.monitor.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StatusEnum;
import com.oner365.monitor.enums.MisfirePolicyEnum;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.util.CronUtils;
import com.oner365.util.DataUtils;
import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 定时任务调度表 nt_sys_task
 *
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_task")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysTask implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
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
  @Type(value = JsonType.class)
  @Column(name = "invoke_param", columnDefinition = "VARCHAR2")
  private InvokeParam invokeParam;

  /**
   * cron执行表达式
   */
  @Column(name = "cron_expression")
  private String cronExpression;

  /**
   * cron计划策略
   */
  @Enumerated
  @Column(name = "misfire_policy", length = 20)
  private MisfirePolicyEnum misfirePolicy = MisfirePolicyEnum.DEFAULT;

  /**
   * 是否并发执行（0允许 1禁止）
   */
  @Column(name = "concurrent", length = 1)
  private String concurrent;

  /**
   * 任务状态（1正常 0暂停）
   */
  @Enumerated
  @Column(name = "status", length = 1)
  private TaskStatusEnum status;

  /**
   * 执行任务状态（0正在执行 1执行完成）
   */
  @Enumerated
  @Column(name = "execute_status", length = 1)
  private StatusEnum executeStatus;

  /**
   * 备注
   */
  @Column(name = "remark", length = 500)
  private String remark;

  /**
   * 创建人
   */
  @CreatedBy
  @Column(name = "create_user", length = 32)
  private String createUser;

  /**
   * 创建时间
   */
  @CreatedDate
  @Column(name = "create_time", updatable = false)
  private Date createTime;

  /**
   * 更新时间
   */
  @LastModifiedDate
  @Column(name = "update_time", updatable = true)
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

  public MisfirePolicyEnum getMisfirePolicy() {
    return misfirePolicy;
  }

  public void setMisfirePolicy(MisfirePolicyEnum misfirePolicy) {
    this.misfirePolicy = misfirePolicy;
  }

  public String getConcurrent() {
    return concurrent;
  }

  public void setConcurrent(String concurrent) {
    this.concurrent = concurrent;
  }

  public TaskStatusEnum getStatus() {
    return status;
  }

  public void setStatus(TaskStatusEnum status) {
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

  public StatusEnum getExecuteStatus() {
    return executeStatus;
  }

  public void setExecuteStatus(StatusEnum executeStatus) {
    this.executeStatus = executeStatus;
  }

}
