package com.oner365.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.constants.ErrorInfo;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.gateway.entity.GatewayRoute;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.gateway.vo.GatewayRouteVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 动态路由控制
 *
 * @author liutao
 *
 */
@RestController
@RequestMapping("/route")
@Api(tags = "动态路由控制")
public class DynamicRouteController extends BaseController {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    /**
     * 路由列表
     *
     * @param data 查询参数
     * @return Page<GatewayRoute>
     */
    @PostMapping("/list")
    @ApiOperation("获取列表")
    public Page<GatewayRoute> list(@RequestBody QueryCriteriaBean data) {
        return dynamicRouteService.pageList(data);
    }

    /**
     * 增加路由
     *
     * @param gatewayRouteVo 路由对象
     * @return ResponseResult<String>
     */
    @PostMapping("/add")
    @ApiOperation("添加路由")
    public ResponseResult<String> add(@RequestBody GatewayRouteVo gatewayRouteVo) {
        if (gatewayRouteVo != null) {
            GatewayRoute gatewayRoute = gatewayRouteVo.toObject();
            String msg = dynamicRouteService.save(gatewayRoute);
            return ResponseResult.success(msg);
        }
        return ResponseResult.error(ErrorInfo.ERR_SAVE_ERROR);
    }

    /**
     * 获取路由
     *
     * @param id 编号
     * @return GatewayRoute
     */
    @GetMapping("/get/{id}")
    @ApiOperation("按id获取路由信息")
    public GatewayRoute get(@PathVariable String id) {
        return dynamicRouteService.getById(id);
    }

    /**
     * 删除路由
     *
     * @param ids 编号
     * @return ResponseResult<String>
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除路由")
    public ResponseResult<String> delete(@RequestBody String... ids) {
        for (String id : ids) {
            dynamicRouteService.delete(id);
        }
        return ResponseResult.success(PublicConstants.SUCCESS);
    }

    /**
     * 更新路由
     *
     * @param gatewayRouteVo 路由对象
     * @return ResponseResult<String>
     */
    @PostMapping("/update")
    @ApiOperation("更新路由")
    public ResponseResult<String> update(@RequestBody GatewayRouteVo gatewayRouteVo) {
        if (gatewayRouteVo != null) {
            String msg = dynamicRouteService.update(gatewayRouteVo.toObject());
            return ResponseResult.success(msg);
        }
        return ResponseResult.error(ErrorInfo.ERR_SAVE_ERROR);
    }

    /**
     * 刷新路由配置
     *
     * @return List<GatewayRoute>
     */
    @GetMapping("/refresh")
    @ApiOperation("刷新路由")
    public List<GatewayRoute> refresh() {
        return dynamicRouteService.refreshRoute();
    }

    /**
     * 更新路由状态
     *
     * @param id 编号
     * @param status 状态
     * @return ResponseResult<String>
     */
    @GetMapping("/updateRouteStatus/{id}/{status}")
    @ApiOperation("更新状态")
    public ResponseResult<String> updateRouteStatus(@PathVariable String id, @PathVariable String status) {
        String msg = dynamicRouteService.updateRouteStatus(id, status);
        return ResponseResult.success(msg);
    }

}
