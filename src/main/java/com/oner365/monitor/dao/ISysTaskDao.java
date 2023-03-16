package com.oner365.monitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.monitor.entity.SysTask;

/**
 * 系统日志接口
 * @author zhaoyong
 */
public interface ISysTaskDao extends JpaRepository<SysTask, String>,JpaSpecificationExecutor<SysTask>{
    
}
