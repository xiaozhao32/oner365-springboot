package com.oner365.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.enums.StatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 基础权限--用户部门nt_sys_user_org
 *
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_user_org")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysUserOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * 部门 org_id
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private SysOrganization sysOrganization;

    /**
     * 用户 user_id
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private SysUser sysUser;

    /**
     * 排序 position_order
     */
    @Column(name = "position_order", nullable = false, length = 10)
    private Integer positionOrder;

    /**
     * 状态 status
     */
    @Enumerated
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    /**
     * 创建时间 create_time
     */
    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间 update_time
     */
    @LastModifiedDate
    @Column(name = "update_time", updatable = true)
    private LocalDateTime updateTime;

    /**
     * Generate constructor
     */
    public SysUserOrg() {
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

    public SysOrganization getSysOrganization() {
        return sysOrganization;
    }

    public void setSysOrganization(SysOrganization sysOrganization) {
        this.sysOrganization = sysOrganization;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Integer getPositionOrder() {
        return positionOrder;
    }

    public void setPositionOrder(Integer positionOrder) {
        this.positionOrder = positionOrder;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

}
