package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.DataSourceConfig;

/**
 * 数据源接口
 * @author zhaoyong
 */
public interface IDataSourceConfigDao
        extends JpaRepository<DataSourceConfig, String>, JpaSpecificationExecutor<DataSourceConfig> {

}
