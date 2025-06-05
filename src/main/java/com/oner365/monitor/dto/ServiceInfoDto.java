package com.oner365.monitor.dto;

import java.io.Serializable;

/**
 * 服务信息
 *
 * @author zhaoyong
 */
public class ServiceInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务编号
     */
    private String serviceId;

    /**
     * 地址
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 路径
     */
    private String uri;

    /**
     * 实例id
     */
    private String instanceId;

    /**
     * 域
     */
    private String scheme;

    public ServiceInfoDto() {
        super();
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

}
