package com.oner365.gateway.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.gateway.entity.GatewayRoute;

/**
 * 网关路由接口
 * @author zhaoyong
 */
@Repository
public interface IGatewayRouteDao extends JpaRepository<GatewayRoute, String>, JpaSpecificationExecutor<GatewayRoute> {

}
