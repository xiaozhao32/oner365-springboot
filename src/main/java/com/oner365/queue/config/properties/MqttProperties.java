package com.oner365.queue.config.properties;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQTT properties
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    /**
     * 账号
     */
    private @Nullable String username;

    /**
     * 密码
     */
    private @Nullable String password;

    /**
     * 地址
     */
    private @Nullable String uri;

    /**
     * client
     */
    private @Nullable String clientId;
    
    /**
     * default-topic
     */
    private @Nullable String defaultTopic;

    /**
     * 构造方法
     */
    public MqttProperties() {
        super();
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

}
