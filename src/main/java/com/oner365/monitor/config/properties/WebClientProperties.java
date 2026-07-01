package com.oner365.monitor.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
    private int maxInMemorySize;

    /**
     * SSL
     */
    private Ssl ssl = new Ssl();

    public static class Ssl {
        
        /**
         * SSL enable
         */
        private boolean enable;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    public int getMaxInMemorySize() {
        return maxInMemorySize;
    }

    public void setMaxInMemorySize(int maxInMemorySize) {
        this.maxInMemorySize = maxInMemorySize;
    }

    public Ssl getSsl() {
        return ssl;
    }

    public void setSsl(Ssl ssl) {
        this.ssl = ssl;
    }
}
