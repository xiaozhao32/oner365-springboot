package com.oner365.gateway.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.oner365.gateway.entity.GatewayRoute;

/**
 * 动态路由服务接口
 * @author zhaoyong
 */
public interface DynamicRouteService {

    /**
     * 路由列表
     *
     * @return List
     */
    List<GatewayRoute> findList();
    
    /**
     * 分页查询路由列表
     * 
     * @param paramJson 路由对象
     * @return Page
     */
    Page<GatewayRoute> pageList(JSONObject paramJson);

    /**
     * 添加路由
     *
     * @param gatewayRoute 路由对象
     * @return String
     */
    String save(GatewayRoute gatewayRoute);

    /**
     * 更新路由
     *
     * @param gatewayRoute 路由对象
     * @return String
     */
    String update(GatewayRoute gatewayRoute);

    /**
     * 根据id查询路由
     *
     * @param id 编号
     * @return GatewayRoute
     */
    GatewayRoute getById(String id);

    /**
     * 删除路由
     *
     * @param id 编号
     */
    void delete(String id);

    /**
     * 刷新路由
     *
     * @return List
     */
    List<GatewayRoute> refreshRoute();

    /**
     * 更新路由状态
     *
     * @param id     编号
     * @param status 状态
     * @return String
     */
    String updateRouteStatus(String id, String status);

    /**
     * 映射路由path状态
     *
     * @param route 路由对象
     * @return Map
     */
    Map<String, String> mapRoute(GatewayRoute route);

}
