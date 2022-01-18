package com.oner365.common.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Token相关配置
 * 
 * @author zhaoyong
 */
@Configuration
public class CommonProperties {
  
  /**
   * redis缓存开关
   */
  @Value("${spring.redis.enable}")
  private boolean redisEnabled;

  /**
   * elasticsearch地址
   */
  @Value("${spring.elasticsearch.uris}")
  private String uris;
  
  /**
   * 项目名称
   */
  @Value("${spring.application.name}")
  private String serviceId;
  
  /**
   * 默认ip地址
   */
  private String host = "localhost";
  
  /**
   * 服务端口
   */
  @Value("${server.port}")
  private int port;
  
  /**
   * 当前服务配置
   */
  @Value("${spring.profiles.active}")
  private String scheme;
  
  /**
   * 构造方法
   */
  public CommonProperties() {
    super();
  }

  public boolean isRedisEnabled() {
    return redisEnabled;
  }

  public void setRedisEnabled(boolean redisEnabled) {
    this.redisEnabled = redisEnabled;
  }

  public String getUris() {
    return uris;
  }

  public void setUris(String uris) {
    this.uris = uris;
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

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

}
