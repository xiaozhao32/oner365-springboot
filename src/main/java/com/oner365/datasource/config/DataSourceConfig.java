package com.oner365.datasource.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.oner365.common.cache.RedisCache;
import com.oner365.datasource.constants.DataSourceConstants;
import com.oner365.datasource.dynamic.DynamicDataSource;
import com.oner365.datasource.util.DataSourceUtil;

/**
 * 数据源配置
 * 
 * @author zhaoyong
 *
 */
@Configuration
public class DataSourceConfig {
  
  private final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
  
  private static final String DS_TYPE = "ds_type";
  
  /**
   * 数据库加载方式 db cache
   */
  @Value("${datasource.type:db}")
  private String datasourceType;
  
  @Resource
  private RedisCache redisCache;
  
  /**
   * 获取数据源
   */
  @Bean(name = "primaryDataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  DataSource primaryDataSource() {
    return new DruidDataSource();
  }

  @Primary
  @Bean(name = "dynamicDataSource")
  @SuppressWarnings("unchecked")
  DynamicDataSource dynamicDataSource() {
    DataSource primarySource = primaryDataSource();
    // 当前数据源
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DataSourceConstants.PRIMARY, primarySource);
    
    if (DataSourceConstants.DS_TYPE_DB.equals(datasourceType)) {
      try {
        String sql = "select connect_name, db_name, ip_address, url, user_name, password, port, driver_name, ds_type from nt_data_source_config";
        List<Map<String, String>> list = DataSourceUtil.execute(primarySource.getConnection(), sql);
        list.forEach(map -> {
          if (DataSourceConstants.DS_TYPE_DB.equals(map.get(DS_TYPE))) {
            // 动态数据源
            DruidDataSource druidDatasource = builderSource(map.get("connect_name"), map);
            targetDataSources.put(druidDatasource.getName(), druidDatasource);
          }
        });
      } catch (Exception e) {
        logger.error("dynamicDataSource dbMap error:", e);
      }
    } else if (DataSourceConstants.DS_TYPE_CACHE.equals(datasourceType)) {
      // redis加载方式
      try {
        Map<String, Object> sourceMap = redisCache.getCacheMap(DataSourceConstants.CACHE_MAP);
        sourceMap.forEach((key, value) -> {
          DruidDataSource druidDatasource = builderSource(key, (Map<String, String>)value);
          targetDataSources.put(druidDatasource.getName(), druidDatasource);
        });
      } catch (Exception e) {
        logger.error("dynamicDataSource sourceMap error:", e);
      }
    }
    
    return new DynamicDataSource(primarySource, targetDataSources);
  }
  
  private DruidDataSource builderSource(String key, Map<String, String> map) {
    try (DruidDataSource datasource = new DruidDataSource()) {
      datasource.setUrl(map.get("url"));
      datasource.setUsername(map.get("user_name"));
      datasource.setPassword(map.get("password"));
      datasource.setDriverClassName(map.get("driver_name"));
      datasource.setDbType("com.alibaba.druid.pool.DruidDataSource");
      datasource.setName(key);
      return datasource;
    }
  }
}
