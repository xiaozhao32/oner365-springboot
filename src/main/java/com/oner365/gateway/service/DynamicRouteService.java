package com.oner365.gateway.service;

import java.util.List;
import java.util.Map;

import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.vo.GatewayRouteVo;

/**
 * 动态路由服务接口
 * 
 * @author zhaoyong
 */
public interface DynamicRouteService extends BaseService {

  /**
   * 路由列表
   *
   * @return List
   */
  List<GatewayRouteDto> findList();

  /**
   * 分页查询路由列表
   * 
   * @param data 查询参数
   * @return PageInfo
   */
  PageInfo<GatewayRouteDto> pageList(QueryCriteriaBean data);

  /**
   * 添加路由
   *
   * @param gatewayRoute 路由对象
   * @return GatewayRouteDto
   */
  GatewayRouteDto save(GatewayRouteVo gatewayRoute);

  /**
   * 更新路由
   *
   * @param gatewayRoute 路由对象
   * @return GatewayRouteDto
   */
  GatewayRouteDto update(GatewayRouteVo gatewayRoute);

  /**
   * 根据id查询路由
   *
   * @param id 编号
   * @return GatewayRouteDto
   */
  GatewayRouteDto getById(String id);

  /**
   * 删除路由
   *
   * @param id 编号
   */
  Boolean deleteById(String id);

  /**
   * 刷新路由
   *
   * @return List
   */
  List<GatewayRouteDto> refreshRoute();

  /**
   * 更新路由状态
   *
   * @param id     编号
   * @param status 状态
   * @return Boolean
   */
  Boolean editStatus(String id, StatusEnum status);

  /**
   * 映射路由path状态
   *
   * @param route 路由对象
   * @return Map
   */
  Map<String, Integer> mapRoute(GatewayRouteDto route);

}
