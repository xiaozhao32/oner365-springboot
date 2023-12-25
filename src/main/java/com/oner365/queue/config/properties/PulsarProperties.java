package com.oner365.queue.config.properties;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * pulsar 相关配置
 * 
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "pulsar")
public class PulsarProperties implements Serializable {
  
  private static final long serialVersionUID = 1L;

  /**
   * 请求地址
   */
  private String url;
  
  /**
   * subscription
   */
  private String subscription;
  
  public PulsarProperties() {
    super();
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the subscription
   */
  public String getSubscription() {
    return subscription;
  }

  /**
   * @param subscription the subscription to set
   */
  public void setSubscription(String subscription) {
    this.subscription = subscription;
  }

}
