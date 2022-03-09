package com.oner365.datasource.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源类型设置
 * 
 * @author zhaoyong
 */
public class DataSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();
    
    /**
     * Generate constructor
     */
    private DataSourceConfig() {
    }

    public static void setDataSource(String dbType) {
        LOGGER.info("切换到 [{}] 数据源", dbType);
        HOLDER.set(dbType);
    }

    public static String getDataSource() {
        return HOLDER.get();
    }

    public static void clearDataSource() {
        HOLDER.remove();
    }
    
}
