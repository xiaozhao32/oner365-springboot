package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.sys.entity.SysRole;

/**
 * 角色接口
 * 
 * @author zhaoyong
 */
@Repository
public interface ISysRoleDao extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole> {

}
