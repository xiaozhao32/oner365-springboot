package com.oner365.monitor.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

/**
 * webclient配置
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "webclient")
public class WebClientProperties {

    /**
     * max-in-memory-size
     */
    private DataSize maxInMemorySize = DataSize.of(1L, DataUnit.GIGABYTES);

    /**
     * SSL
     */
    private final Ssl ssl = new Ssl();

    public static class Ssl {

        /**
         * SSL enable
         */
        private boolean enable = true;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

    }

    public DataSize getMaxInMemorySize() {
        return maxInMemorySize;
    }

    public void setMaxInMemorySize(DataSize maxInMemorySize) {
        this.maxInMemorySize = maxInMemorySize;
    }

    public Ssl getSsl() {
        return ssl;
    }

}
