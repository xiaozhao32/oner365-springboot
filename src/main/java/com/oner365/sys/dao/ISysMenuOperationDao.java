package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.SysMenuOperation;

/**
 * 菜单操作接口
 *
 * @author zhaoyong
 */
public interface ISysMenuOperationDao
        extends JpaRepository<SysMenuOperation, String>, JpaSpecificationExecutor<SysMenuOperation> {

}
