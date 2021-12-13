package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.service.BaseService;
import com.oner365.sys.dto.SysDictItemTypeDto;
import com.oner365.sys.vo.SysDictItemTypeVo;

/**
 * 字典类型接口
 * 
 * @author zhaoyong
 */
public interface ISysDictItemTypeService extends BaseService {

  /**
   * 添加
   * 
   * @param type 字典类型
   * @return SysDictItemTypeDto
   */
  SysDictItemTypeDto save(SysDictItemTypeVo type);

  /**
   * 按编号查询详情
   * 
   * @param id 主键
   * @return SysDictItemTypeDto
   */
  SysDictItemTypeDto getById(String id);

  /**
   * 查询分页列表
   * 
   * @param data 查询参数
   * @return PageInfo
   */
  PageInfo<SysDictItemTypeDto> pageList(QueryCriteriaBean data);

  /**
   * 查询列表
   * 
   * @param data 查询参数
   * @return List
   */
  List<SysDictItemTypeDto> findList(QueryCriteriaBean data);

  /**
   * 检测code是否存在
   * 
   * @param id   主键
   * @param code 编号
   * @return long
   */
  long checkCode(String id, String code);

  /**
   * 删除
   * 
   * @param id 主键
   * @return int
   */
  int deleteById(String id);

  /**
   * 查询列表
   * 
   * @param codeList 查询参数
   * @return List
   */
  List<SysDictItemTypeDto> findListByCodes(List<String> codeList);

  /**
   * 修改状态
   * 
   * @param id     主键
   * @param status 状态
   * @return int
   */
  Integer editStatus(String id, String status);

}
