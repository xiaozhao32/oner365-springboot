package com.oner365.gateway.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.common.enums.StatusEnum;
import com.vladmihalcea.hibernate.type.json.JsonType;

/**
 * Gateway的路由定义模型
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_gateway_route")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "transportOrders" })
public class GatewayRoute implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 路由的Id
   */
  @Id
  private String id;

  /**
   * 路由断言集合配置
   */
  @Type(value = JsonType.class)
  @Column(name = "predicates", columnDefinition = "VARCHAR2")
  private List<GatewayPredicate> predicates;

  /**
   * 路由过滤器集合配置
   */
  @Type(value = JsonType.class)
  @Column(name = "filters", columnDefinition = "VARCHAR2")
  private List<GatewayFilter> filters;

  /**
   * 路由规则转发的目标uri
   */
  @Column(name = "uri", nullable = false)
  private String uri;

  /**
   * 路由执行的顺序
   */
  @Column(name = "route_order", nullable = false, length = 11)
  private Integer routeOrder = 0;

  /**
   * 路由状态 0：可用 1：不可用
   */
  @Enumerated
  @Column(name = "status", nullable = false)
  private StatusEnum status;

  /**
   * 界面使用的谓词
   */
  @Transient
  private String pattern;

  public GatewayRoute() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<GatewayPredicate> getPredicates() {
    return predicates;
  }

  public void setPredicates(List<GatewayPredicate> predicates) {
    this.predicates = predicates;
  }

  public List<GatewayFilter> getFilters() {
    return filters;
  }

  public void setFilters(List<GatewayFilter> filters) {
    this.filters = filters;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public Integer getRouteOrder() {
    return routeOrder;
  }

  public void setRouteOrder(Integer routeOrder) {
    this.routeOrder = routeOrder;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

}
