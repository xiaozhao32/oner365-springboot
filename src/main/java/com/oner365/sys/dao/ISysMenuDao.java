package com.oner365.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.oner365.sys.entity.SysMenu;

/**
 * 菜单接口
 *
 * @author zhaoyong
 */
public interface ISysMenuDao extends JpaRepository<SysMenu, String>, JpaSpecificationExecutor<SysMenu> {

    /**
     * 按菜单编号查询
     * @param typeCode 菜单编号
     * @return List
     */
    @Query(value = "select m from SysMenu m, SysMenuType t where m.menuTypeId=t.id and m.status=1 and t.typeCode=?1")
    List<SysMenu> findMenuByTypeCode(String typeCode);

}
