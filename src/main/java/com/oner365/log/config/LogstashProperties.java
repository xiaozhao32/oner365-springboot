package com.oner365.log.config;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Logstash logback-spring.xml properties
 * 
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "logging.logstash")
public class LogstashProperties {

    /**
     * logback-spring.xml logstash is enabled
     */
    private boolean enabled = false;

    /**
     * logback-spring.xml logstash address
     */
    private @Nullable String addresses;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

}
