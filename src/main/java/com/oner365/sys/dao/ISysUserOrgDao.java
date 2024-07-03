package com.oner365.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.sys.entity.SysUserOrg;

/**
 * 用户机构权限接口
 *
 * @author zhaoyong
 */
public interface ISysUserOrgDao extends JpaRepository<SysUserOrg, String>,JpaSpecificationExecutor<SysUserOrg>{

    /**
     * 查询机构权限列表
     * @param userId 用户编号
     * @return List
     */
    @Query(value = "select sysOrganization.id from SysUserOrg where sysUser.id=?1")
    List<String> findUserOrgByUserId(String userId);

    /**
     * 删除用户机构权限
     * @param userId 用户编号
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Query(value = "delete from SysUserOrg where sysUser.id=?1 ")
    void deleteUserOrgByUserId(String userId);
}
