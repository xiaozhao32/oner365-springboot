package com.oner365.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.enums.ResultEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
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

  protected static Map<String, String> predicateMap = new HashMap<>();

  @Override
  public List<GatewayRouteDto> findList() {
    return convert(gatewayRouteDao.findAll(), GatewayRouteDto.class);
  }

  @Override
  public PageInfo<GatewayRouteDto> pageList(QueryCriteriaBean data) {
    try {
      Page<GatewayRoute> page = gatewayRouteDao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, GatewayRouteDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public String save(GatewayRouteVo gatewayRoute) {

    // Filter
    List<GatewayFilter> filters = new ArrayList<>();
    GatewayFilter gatewayFilter = new GatewayFilter();
    gatewayFilter.setName("StripPrefix");
    Map<String, String> argsFilter = new HashMap<>(10);
    argsFilter.put("parts", "1");
    gatewayFilter.setArgs(argsFilter);
    filters.add(gatewayFilter);
    gatewayRoute.setFilters(filters);

    // Predicates
    List<GatewayPredicate> predicates = new ArrayList<>();
    GatewayPredicate gatewayPredicate = new GatewayPredicate();
    gatewayPredicate.setName("Path");
    Map<String, String> args = new HashMap<>(10);
    args.put("pattern", gatewayRoute.getPattern());
    gatewayPredicate.setArgs(args);
    predicates.add(gatewayPredicate);
    gatewayRoute.setPredicates(predicates);

    // 页面保存信息
    gatewayRouteDao.save(convert(gatewayRoute, GatewayRoute.class));
    return ResultEnum.SUCCESS.getName();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public String update(GatewayRouteVo gatewayRoute) {
    delete(gatewayRoute.getId());
    return save(gatewayRoute);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public void delete(String id) {
    gatewayRouteDao.deleteById(id);
    syncRouteMqService.syncRoute();
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
      syncRouteMqService.syncRoute();
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
    return convert(findById(id), GatewayRouteDto.class);
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
