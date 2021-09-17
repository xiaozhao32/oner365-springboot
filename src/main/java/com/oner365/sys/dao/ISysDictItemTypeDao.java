package com.oner365.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oner365.sys.entity.SysDictItemType;

/**
 * 字典类型接口
 * 
 * @author zhaoyong
 */
public interface ISysDictItemTypeDao extends JpaRepository<SysDictItemType, String>, JpaSpecificationExecutor<SysDictItemType> {

    /**
     * 查询列表
     * 
     * @param codes 字典编码
     * @return List<SysDictItemType>
     */
    @Query(value = "select * from nt_sys_dict_item_type where dict_type_code in (:codes)", nativeQuery = true)
    List<SysDictItemType> findListByCode(@Param("codes") List<String> codes);

}
