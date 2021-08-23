package com.oner365.gateway.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.controller.BaseController;
import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.entity.GatewayRoute;
import com.oner365.gateway.service.DynamicRouteService;
import com.google.common.collect.Maps;

/**
 * 动态路由控制
 *
 * @author liutao
 *
 */
@RestController
@RequestMapping("/route")
public class DynamicRouteController extends BaseController {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    /**
     * 路由列表
     *
     * @param paramJson 查询参数
     * @return Page<GatewayRoute>
     */
    @PostMapping("/list")
    public Page<GatewayRoute> list(@RequestBody JSONObject paramJson) {
        return dynamicRouteService.pageList(paramJson);
    }

    /**
     * 增加路由
     *
     * @param paramJson 路由对象
     * @return Map<String, Object>
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody JSONObject paramJson) {
        GatewayRoute gatewayRoute = JSON.toJavaObject(paramJson, GatewayRoute.class);
        String msg = dynamicRouteService.save(gatewayRoute);

        Map<String, Object> result = Maps.newHashMap();
        result.put(GatewayConstants.CODE, GatewayConstants.SUCCESS_CODE);
        result.put(GatewayConstants.MSG, msg);
        return result;
    }

    /**
     * 获取路由
     *
     * @param id 编号
     * @return GatewayRoute
     */
    @GetMapping("/get/{id}")
    public GatewayRoute get(@PathVariable String id) {
        return dynamicRouteService.getById(id);
    }

    /**
     * 删除路由
     *
     * @param ids 编号
     * @return Map<String, Object>
     */
    @DeleteMapping("/delete")
    public Map<String, Object> delete(@RequestBody String... ids) {
        for (String id : ids) {
            dynamicRouteService.delete(id);
        }

        Map<String, Object> result = Maps.newHashMap();
        result.put(GatewayConstants.CODE, GatewayConstants.SUCCESS_CODE);
        return result;
    }

    /**
     * 更新路由
     *
     * @param paramJson 路由对象
     * @return Map<String, Object>
     */
    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody JSONObject paramJson) {
        GatewayRoute gatewayRoute = JSON.toJavaObject(paramJson, GatewayRoute.class);
        String msg = dynamicRouteService.update(gatewayRoute);

        Map<String, Object> result = Maps.newHashMap();
        result.put(GatewayConstants.CODE, GatewayConstants.SUCCESS_CODE);
        result.put(GatewayConstants.MSG, msg);
        return result;
    }

    /**
     * 刷新路由配置
     *
     * @return List<GatewayRoute>
     */
    @GetMapping("/refresh")
    public List<GatewayRoute> refresh() {
        return dynamicRouteService.refreshRoute();
    }

    /**
     * 更新路由状态
     *
     * @param id 编号
     * @param status 状态
     * @return Map<String, Object>
     */
    @GetMapping("/updateRouteStatus/{id}/{status}")
    public Map<String, Object> updateRouteStatus(@PathVariable String id, @PathVariable String status) {
        String msg = dynamicRouteService.updateRouteStatus(id, status);

        Map<String, Object> result = Maps.newHashMap();
        result.put(GatewayConstants.CODE, GatewayConstants.SUCCESS_CODE);
        result.put(GatewayConstants.MSG, msg);
        return result;
    }

}
