package com.oner365.gateway.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.dao.IGatewayRouteDao;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.entity.GatewayFilter;
import com.oner365.gateway.entity.GatewayPredicate;
import com.oner365.gateway.entity.GatewayRoute;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.gateway.vo.GatewayRouteVo;
import com.oner365.queue.service.IQueueSendService;

/**
 * 动态路由服务实现类
 *
 * @author zhaoyong
 */
@Service
public class DynamicRouteServiceImpl implements DynamicRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicRouteServiceImpl.class);

    @Resource
    private IGatewayRouteDao gatewayRouteDao;

    @Resource
    private IQueueSendService queueSendService;

    protected static Map<String, Integer> predicateMap = new HashMap<>();

    @Override
    public List<GatewayRouteDto> findList() {
        return convert(gatewayRouteDao.findAll(), GatewayRouteDto.class);
    }

    @Override
    public PageInfo<GatewayRouteDto> pageList(QueryCriteriaBean data) {
        try {
            Page<GatewayRoute> page = gatewayRouteDao.findAll(QueryUtils.buildCriteria(data),
                    QueryUtils.buildPageRequest(data));
            return convert(page, GatewayRouteDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public GatewayRouteDto save(GatewayRouteVo gatewayRoute) {

        // Filter
        GatewayFilter gatewayFilter = new GatewayFilter();
        gatewayFilter.setName("StripPrefix");
        Map<String, String> argsFilter = new HashMap<>(10);
        argsFilter.put("parts", "1");
        gatewayFilter.setArgs(argsFilter);
        gatewayRoute.setFilters(Collections.singletonList(gatewayFilter));

        // Predicates
        GatewayPredicate gatewayPredicate = new GatewayPredicate();
        gatewayPredicate.setName("Path");
        Map<String, String> args = new HashMap<>(10);
        args.put("pattern", gatewayRoute.getPattern());
        gatewayPredicate.setArgs(args);
        gatewayRoute.setPredicates(Collections.singletonList(gatewayPredicate));

        // 页面保存信息
        GatewayRoute entity = gatewayRouteDao.save(convert(gatewayRoute, GatewayRoute.class));
        return convert(entity, GatewayRouteDto.class);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public GatewayRouteDto update(GatewayRouteVo gatewayRoute) {
        deleteById(gatewayRoute.getId());
        return save(gatewayRoute);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public Boolean deleteById(String id) {
        gatewayRouteDao.deleteById(id);
        queueSendService.syncRoute();
        return Boolean.TRUE;
    }

    @Override
    public List<GatewayRouteDto> refreshRoute() {
        List<GatewayRouteDto> list = findList();
        list.forEach(this::mapRoute);
        return list;
    }

    @Override
    public Boolean editStatus(String id, StatusEnum status) {
        GatewayRoute gatewayRoute = findById(id);
        if (gatewayRoute != null) {
            gatewayRoute.setStatus(status);
            gatewayRouteDao.save(gatewayRoute);
            queueSendService.syncRoute();
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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
    public Map<String, Integer> mapRoute(GatewayRouteDto route) {
        route.getPredicates()
            .stream()
            .filter(predicate -> predicate.getName().equals(GatewayConstants.PREDICATE_NAME))
            .forEach(predicates -> {
                String pattern = StringUtils.substring(
                        predicates.getArgs().get(GatewayConstants.PREDICATE_ARGS_PATTERN), 0,
                        predicates.getArgs().get(GatewayConstants.PREDICATE_ARGS_PATTERN).length() - 2);
                predicateMap.put(pattern,
                        DataUtils.isEmpty(route.getStatus()) ? StatusEnum.NO.ordinal() : route.getStatus().ordinal());
            });
        return predicateMap;
    }

}
