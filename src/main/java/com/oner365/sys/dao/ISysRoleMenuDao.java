package com.oner365.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.sys.entity.SysRoleMenu;

/**
 * 菜单角色权限接口
 *
 * @author zhaoyong
 */
public interface ISysRoleMenuDao extends JpaRepository<SysRoleMenu, String>, JpaSpecificationExecutor<SysRoleMenu> {

    /**
     * 查询菜单角色权限列表
     * @param roleId 角色编号
     * @param menuTypeId 菜单类型编号
     * @return List
     */
    @Query(value = "select menuId from SysRoleMenu where roleId=?1 and menuTypeId=?2")
    List<String> findMenuListByRoleId(String roleId, String menuTypeId);

    /**
     * 删除菜单角色权限
     * @param roleId 角色编号
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Query(value = "delete from SysRoleMenu where roleId=?1")
    void deleteRoleMenuByRoleId(String roleId);

    /**
     * 删除菜单角色权限
     * @param menuId 菜单编号
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Query(value = "delete from SysRoleMenu where menuId=?1")
    void deleteByMenuId(String menuId);

}
