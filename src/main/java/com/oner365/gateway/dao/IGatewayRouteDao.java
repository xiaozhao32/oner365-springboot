package com.oner365.gateway.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.gateway.entity.GatewayRoute;

/**
 * 网关路由接口
 *
 * @author zhaoyong
 */
public interface IGatewayRouteDao
        extends JpaRepository<GatewayRoute, String>, JpaSpecificationExecutor<GatewayRoute>, Serializable {

}
