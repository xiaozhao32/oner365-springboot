package com.oner365.sys.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.sys.dao.ISysMenuTypeDao;
import com.oner365.sys.entity.SysMenuType;
import com.oner365.sys.service.ISysMenuTypeService;

/**
 * SysMenuType Service
 *
 * @author zhaoyong
 */
@Service
public class SysMenuTypeServiceImpl implements ISysMenuTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuTypeServiceImpl.class);

    private static final String CACHE_NAME = "SysMenuType";
    private static final String CACHE_MENU_NAME = "SysMenu";

    @Autowired
    private ISysMenuTypeDao dao;

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public Page<SysMenuType> pageList(QueryCriteriaBean data) {
        try {
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return dao.findAll(QueryUtils.buildCriteria(data), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysMenuType> findList(QueryCriteriaBean data) {
        try {
            return dao.findAll(QueryUtils.buildCriteria(data));
        } catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return new ArrayList<>();
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysMenuType getById(String id) {
        try {
            Optional<SysMenuType> optional = dao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public SysMenuType save(SysMenuType menuType) {
        if (Strings.isNullOrEmpty(menuType.getId())) {
            menuType.setStatus(PublicConstants.STATUS_YES);
            menuType.setCreateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            menuType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        }
        return dao.save(menuType);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public int editStatusById(String id, String status) {
        SysMenuType entity = getById(id);
        if (entity != null && entity.getId() != null) {
            entity.setStatus(status);
            save(entity);
            return 1;
        }
        return 0;
    }

    @Override
    public int checkCode(String id, String code) {
        try {
            return dao.countTypeById(id, code);
        } catch (Exception e) {
            LOGGER.error("Error checkCode: ", e);
        }
        return 0;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public SysMenuType getMenuTypeByTypeCode(String menuType) {
        return dao.getMenuTypeByTypeCode(menuType);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public int deleteById(String id) {
        dao.deleteById(id);
        return 1;
    }

}
