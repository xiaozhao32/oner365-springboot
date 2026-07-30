package com.oner365.data.commons.config.properties;

import java.time.Duration;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Token相关配置
 *
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "token")
public class AccessTokenProperties {

    /**
     * token密钥
     */
    private @Nullable String secret;

    /**
     * token过期时间
     */
    private Duration expireTime = Duration.ofMinutes(720L);

    /**
     * 构造方法
     */
    public AccessTokenProperties() {
        super();
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Duration getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Duration expireTime) {
        this.expireTime = expireTime;
    }

}
