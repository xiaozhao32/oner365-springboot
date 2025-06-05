package com.oner365.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.sys.entity.SysRoleMenuOper;

/**
 * 菜单操作权限接口
 *
 * @author zhaoyong
 */
public interface ISysRoleMenuOperDao
        extends JpaRepository<SysRoleMenuOper, String>, JpaSpecificationExecutor<SysRoleMenuOper> {

    /**
     * 查询操作权限列表
     * @param roleId 角色编号
     * @param menuTypeId 菜单类型编号
     * @return List
     */
    @Query(value = "from SysRoleMenuOper where roleId=?1 and menuTypeId=?2")
    List<SysRoleMenuOper> findMenuOperListByRoleId(String roleId, String menuTypeId);

    /**
     * 删除操作权限
     * @param roleId 角色编号
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Query(value = "delete from SysRoleMenuOper where roleId=?1")
    void deleteRoleMenuOperByRoleId(String roleId);

    /**
     * 查询操作权限列表
     * @param roles 角色
     * @param menuId 菜单编号
     * @return List
     */
    @Query(value = "select DISTINCT b.id as operId,b.operationName as operName,b.operationType as operType from SysRoleMenuOper a, SysMenuOperation b where a.operationId=b.id and a.roleId in (:roles) and a.menuId=:menuId")
    List<Map<String, String>> findMenuOperByRoles(@Param("roles") List<String> roles, @Param("menuId") String menuId);

}
