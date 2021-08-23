package com.oner365.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.sys.entity.SysUserJob;

/**
 * 职位接口
 * @author zhaoyong
 */
public interface ISysUserJobDao extends JpaRepository<SysUserJob, String>,JpaSpecificationExecutor<SysUserJob>{

    /**
     * 查询职位列表
     * @param userId 用户编号
     * @return List
     */
    @Query(value = "select o.id from nt_sys_job o, nt_sys_user_job s where o.id=s.job_id and s.user_id=?1",nativeQuery = true)
    List<String> findUserJobByUserId(String userId);

    /**
     * 删除职位
     * @param userId 用户编号
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Query(value = "delete from SysUserJob where sysUser.id=?1 ")
    void deleteUserJobByUserId(String userId);
}
