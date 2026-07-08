package com.oner365.monitor.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.monitor.dao.ISysTaskLogDao;
import com.oner365.monitor.dto.SysTaskLogDto;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.mapper.SysTaskLogMapper;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.vo.SysTaskLogVo;

import jakarta.annotation.Resource;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author liutao
 */
@Service
public class SysTaskLogServiceImpl implements ISysTaskLogService {


    @Resource
    private ISysTaskLogDao dao;

    @Resource
    private SysTaskLogMapper taskLogMapper;

    @Override
    public PageInfo<SysTaskLogDto> pageList(QueryCriteriaBean data) {
        Page<SysTaskLog> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
        return convert(page, SysTaskLogDto.class);
    }

    @Override
    public List<SysTaskLogDto> findList(QueryCriteriaBean data) {
        if (data.getOrder() == null) {
            return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysTaskLogDto.class);
        }
        List<SysTaskLog> list = dao.findAll(QueryUtils.buildCriteria(data),
                Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
        return convert(list, SysTaskLogDto.class);
    }

    @Override
    public SysTaskLogDto selectTaskLogById(String id) {
        Optional<SysTaskLog> optional = dao.findById(id);
        return convert(optional.orElse(null), SysTaskLogDto.class);
    }

    @Override
    public Boolean addTaskLog(SysTaskLogVo vo) {
        dao.save(convert(vo, SysTaskLog.class));
        return Boolean.TRUE;
    }

    @Override
    public List<Boolean> deleteTaskLogByIds(String[] ids) {
        return Arrays.stream(ids).map(this::deleteTaskLogById).toList();
    }

    @Override
    public Boolean deleteTaskLogById(String id) {
        dao.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean cleanTaskLog() {
        taskLogMapper.cleanTaskLog();
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteTaskLogByCreateTime(String time) {
        dao.deleteTaskLogByCreateTime(time);
        return Boolean.TRUE;
    }

}
