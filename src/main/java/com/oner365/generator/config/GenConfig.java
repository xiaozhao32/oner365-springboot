package com.oner365.generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取代码生成相关配置
 * 
 * @author zhaoyong
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = { "classpath:generator.yml" })
public class GenConfig {
    
    /** 作者 */
    @Value("${author}")
    private String author;

    /** 生成包路径 */
    @Value("${packageName}")
    private String packageName;

    /** 自动去除表前缀，默认是false */
    @Value("${autoRemovePre}")
    private boolean autoRemovePre;

    /** 表前缀(类名不会包含表前缀) */
    @Value("${tablePrefix}")
    private String tablePrefix;

    public GenConfig() {
        super();
    }
    
    public String getAuthor() {
        return author;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the autoRemovePre
     */
    public boolean getAutoRemovePre() {
        return autoRemovePre;
    }

    /**
     * @param autoRemovePre the autoRemovePre to set
     */
    public void setAutoRemovePre(boolean autoRemovePre) {
        this.autoRemovePre = autoRemovePre;
    }

    /**
     * @return the tablePrefix
     */
    public String getTablePrefix() {
        return tablePrefix;
    }

    /**
     * @param tablePrefix the tablePrefix to set
     */
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

}
