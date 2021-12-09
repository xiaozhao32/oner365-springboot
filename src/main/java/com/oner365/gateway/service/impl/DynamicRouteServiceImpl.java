package com.oner365.gateway.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.dao.IGatewayRouteDao;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.entity.GatewayFilter;
import com.oner365.gateway.entity.GatewayPredicate;
import com.oner365.gateway.entity.GatewayRoute;
import com.oner365.gateway.rabbitmq.ISyncRouteMqService;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.gateway.vo.GatewayRouteVo;
import com.oner365.util.DataUtils;

/**
 * 动态路由服务实现类
 * 
 * @author zhaoyong
 */
@Service
public class DynamicRouteServiceImpl implements DynamicRouteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DynamicRouteServiceImpl.class);

  @Autowired
  private IGatewayRouteDao gatewayRouteDao;

  @Autowired
  private ISyncRouteMqService syncRouteMqService;

  protected static Map<String, String> predicateMap = Maps.newHashMap();

  @Override
  public List<GatewayRouteDto> findList() {
    return convertDto(gatewayRouteDao.findAll());
  }

  @Override
  public Page<GatewayRouteDto> pageList(QueryCriteriaBean data) {
    try {
      Pageable pageable = QueryUtils.buildPageRequest(data);
      return convertDto(gatewayRouteDao.findAll(QueryUtils.buildCriteria(data), pageable));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  public String save(GatewayRouteVo gatewayRoute) {

    // Filter
    List<GatewayFilter> filters = Lists.newArrayList();
    GatewayFilter gatewayFilter = new GatewayFilter();
    gatewayFilter.setName("StripPrefix");
    Map<String, String> argsFilter = Maps.newHashMap();
    argsFilter.put("parts", "1");
    gatewayFilter.setArgs(argsFilter);
    filters.add(gatewayFilter);
    gatewayRoute.setFilters(filters);

    // Predicates
    List<GatewayPredicate> predicates = Lists.newArrayList();
    GatewayPredicate gatewayPredicate = new GatewayPredicate();
    gatewayPredicate.setName("Path");
    Map<String, String> args = Maps.newHashMap();
    args.put("pattern", gatewayRoute.getPattern());
    gatewayPredicate.setArgs(args);
    predicates.add(gatewayPredicate);
    gatewayRoute.setPredicates(predicates);

    // 页面保存信息
    GatewayRoute entity = toPojo(gatewayRoute);
    gatewayRouteDao.save(entity);
    return ResultEnum.SUCCESS.getName();
  }

  private GatewayRoute toPojo(GatewayRouteVo vo) {
    GatewayRoute result = new GatewayRoute();
    result.setId(vo.getId());
    result.setFilters(vo.getFilters());
    result.setPattern(vo.getPattern());
    result.setPredicates(vo.getPredicates());
    result.setRouteOrder(vo.getRouteOrder());
    result.setStatus(vo.getStatus());
    result.setUri(vo.getUri());
    return result;
  }

  @Override
  public String update(GatewayRouteVo gatewayRoute) {
    try {
      delete(gatewayRoute.getId());
      return save(gatewayRoute);
    } catch (Exception e) {
      LOGGER.error("update error:", e);
    }
    return null;
  }

  @Override
  public void delete(String id) {
    try {
      gatewayRouteDao.deleteById(id);
      syncRouteMqService.syncRoute();
    } catch (Exception e) {
      LOGGER.error("delete error:", e);
    }
  }

  @Override
  public List<GatewayRouteDto> refreshRoute() {
    List<GatewayRouteDto> list = findList();
    list.forEach(this::mapRoute);
    return list;
  }

  @Override
  public String updateRouteStatus(String id, String status) {
    GatewayRoute gatewayRoute = findById(id);
    if (gatewayRoute != null) {
      gatewayRoute.setStatus(status);
      gatewayRouteDao.save(gatewayRoute);
      return ResultEnum.SUCCESS.getName();
    }
    return null;
  }
  
  private GatewayRoute findById(String id) {
    Optional<GatewayRoute> optional = gatewayRouteDao.findById(id);
    if (optional.isPresent()) {
      GatewayRoute entity = optional.get();
      if (!entity.getPredicates().isEmpty()) {
        String pattern = entity.getPredicates().get(0).getArgs().get("pattern");
        entity.setPattern(pattern);
      }
    }
    return optional.orElse(null);
  }

  @Override
  public GatewayRouteDto getById(String id) {
    return convertDto(findById(id));
  }

  @Override
  public Map<String, String> mapRoute(GatewayRouteDto route) {
    route.getPredicates().stream().filter(predicate -> predicate.getName().equals(GatewayConstants.PREDICATE_NAME))
        .forEach(predicates -> {
          String pattern = StringUtils.substring(predicates.getArgs().get(GatewayConstants.PREDICATE_ARGS_PATTERN), 0,
              predicates.getArgs().get(GatewayConstants.PREDICATE_ARGS_PATTERN).length() - 2);
          predicateMap.put(pattern,
              DataUtils.isEmpty(route.getStatus()) ? GatewayConstants.ROUT_STATUS_DISABLE : route.getStatus());
        });
    return predicateMap;
  }

}
