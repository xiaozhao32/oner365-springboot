package com.oner365.monitor.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.monitor.dao.ISysTaskLogDao;
import com.oner365.monitor.dto.SysTaskLogDto;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.mapper.SysTaskLogMapper;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author liutao
 */
@Service
public class SysTaskLogServiceImpl implements ISysTaskLogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysTaskLogServiceImpl.class);

  @Autowired
  private ISysTaskLogDao dao;

  @Autowired
  private SysTaskLogMapper taskLogMapper;

  /**
   * 获取quartz调度器日志的计划任务
   *
   * @param data 查询参数
   * @return 调度任务日志集合
   */
  @Override
  public PageInfo<SysTaskLogDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysTaskLog> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysTaskLogDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  /**
   * 通过调度任务日志ID查询调度信息
   *
   * @param id 调度任务日志ID
   * @return 调度任务日志对象信息
   */
  @Override
  public SysTaskLogDto selectTaskLogById(String id) {
    Optional<SysTaskLog> optional = dao.findById(id);
    return convert(optional.orElse(null), SysTaskLogDto.class);
  }

  /**
   * 新增任务日志
   *
   * @param vo 调度日志信息
   */
  @Override
  public void addTaskLog(SysTaskLogVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setCreateTime(DateUtil.getDate());
    }
    dao.save(convert(vo, SysTaskLog.class));
  }

  /**
   * 批量删除调度日志信息
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  @Override
  public int deleteTaskLogByIds(String[] ids) {
    int result = ResultEnum.ERROR.getCode();
    for (String id : ids) {
      result = deleteTaskLogById(id);
    }
    return result;
  }

  /**
   * 删除任务日志
   *
   * @param id 调度日志ID
   */
  @Override
  public int deleteTaskLogById(String id) {
    dao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

  /**
   * 清空任务日志
   */
  @Override
  public void cleanTaskLog() {
    taskLogMapper.cleanTaskLog();
  }

  @Override
  public String deleteTaskLogByCreateTime(String time) {
    try {
      dao.deleteTaskLogByCreateTime(time);
      return StatusEnum.YES.getCode();
    } catch (Exception e) {
      LOGGER.error("Error deleteTaskLogByCreateTime: ", e);
      return StatusEnum.NO.getCode();
    }

  }
}
