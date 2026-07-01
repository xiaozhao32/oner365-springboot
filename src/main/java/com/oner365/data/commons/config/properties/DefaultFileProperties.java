package com.oner365.data.commons.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件内容处理配置
 *
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "file")
public class DefaultFileProperties {

    /**
     * 文件存储类型: local minio
     */
    private String storage;

    /**
     * 文件下载地址
     */
    private String download;

    /**
     * excel导入导出后缀
     */
    private Excel excel;

    public static class Excel {

        /**
         * excel suffix default "xlsx" or "xls"
         */
        private String suffix = "xlsx";

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
    }

    /**
     * 构造方法
     */
    public DefaultFileProperties() {
        super();
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public Excel getExcel() {
        return excel;
    }

    public void setExcel(Excel excel) {
        this.excel = excel;
    }

}
