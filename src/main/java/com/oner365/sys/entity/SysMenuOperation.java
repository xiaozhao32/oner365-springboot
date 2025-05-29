package com.oner365.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.enums.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 操作对象
 *
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_operation")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMenuOperation implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "operation_name", nullable = false, length = 32)
    private String operationName;

    @Column(name = "operation_type", nullable = false, length = 32)
    private String operationType;

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
    @Column(name = "update_time", insertable = false)
    private LocalDateTime updateTime;

    /**
     * Constructor
     */
    public SysMenuOperation() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
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

}
