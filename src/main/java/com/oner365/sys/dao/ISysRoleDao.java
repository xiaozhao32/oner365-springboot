package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.SysRole;

/**
 * 角色接口
 * 
 * @author zhaoyong
 */
public interface ISysRoleDao extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole> {

}
