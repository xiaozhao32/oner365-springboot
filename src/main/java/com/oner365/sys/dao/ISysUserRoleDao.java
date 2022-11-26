package com.oner365.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.sys.entity.SysUserRole;

/**
 * 用户角色权限接口
 * @author zhaoyong
 */
@Repository
public interface ISysUserRoleDao extends JpaRepository<SysUserRole, String>,JpaSpecificationExecutor<SysUserRole>{

    /**
     * 查询列表
     * @param userId 用户编号
     * @return List
     */
    @Query(value = "select sysRole.id from SysUserRole where sysUser.id=?1")
    List<String> findUserRoleByUserId(String userId);

    /**
     * 删除用户角色权限
     * @param userId 用户编号
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Query(value = "delete from SysUserRole where sysUser.id=?1 ")
    void deleteUserRoleByUserId(String userId);

    /**
     * 删除用户角色权限
     * @param roleId 角色编号
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Query(value = "delete from SysUserRole where sysRole.id=?1 ")
    void deleteUserRoleByRoleId(String roleId);

}
