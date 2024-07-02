package com.oner365.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.sys.entity.SysMenuOper;


/**
 * 菜单操作接口
 * @author zhaoyong
 */
public interface ISysMenuOperDao extends JpaRepository<SysMenuOper, String>,JpaSpecificationExecutor<SysMenuOper>{

    /**
     * 根据菜单主键查询
     * @param menuId 菜单主键
     * @return List
     */
    @Query(value = "select sysMenuOperation.id from SysMenuOper where menuId=?1")
    List<String> selectByMenuId(String menuId);

    /**
     * 删除菜单操作
     * @param menuId 菜单主键
     */
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysMenuOper where menuId=?1 ")
    void deleteByMenuId(String menuId);

    /**
     * 删除菜单操作
     * @param operationId 操作id
     */
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysMenuOper where sysMenuOperation.id=?1 ")
    void deleteByOperationId(String operationId);
}
