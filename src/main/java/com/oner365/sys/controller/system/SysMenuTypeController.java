package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.SysMenuTypeDto;
import com.oner365.sys.service.ISysMenuTypeService;
import com.oner365.sys.vo.SysMenuTypeVo;
import com.oner365.sys.vo.check.CheckCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 菜单类型管理
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "系统管理 - 菜单类型")
@RequestMapping("/system/menuType")
public class SysMenuTypeController extends BaseController {

  @Autowired
  private ISysMenuTypeService menuTypeService;

  /**
   * 列表
   *
   * @param data 参数
   * @return PageInfo<SysMenuTypeDto>
   */
  @ApiOperation("1.获取分页列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysMenuTypeDto> list(@RequestBody QueryCriteriaBean data) {
    return menuTypeService.pageList(data);
  }

  /**
   * 列表
   *
   * @return List<SysMenuTypeDto>
   */
  @ApiOperation("2.获取全部有效类型")
  @ApiOperationSupport(order = 2)
  @GetMapping("/findAll")
  public List<SysMenuTypeDto> findAll() {
    QueryCriteriaBean data = new QueryCriteriaBean();
    List<AttributeBean> whereList = new ArrayList<>();
    AttributeBean attribute = new AttributeBean(SysConstants.STATUS, StatusEnum.YES.getCode());
    whereList.add(attribute);
    data.setWhereList(whereList);
    return menuTypeService.findList(data);
  }

  /**
   * 获取信息
   *
   * @param id 编号
   * @return SysMenuTypeDto
   */
  @ApiOperation("3.按id查询")
  @ApiOperationSupport(order = 3)
  @GetMapping("/get/{id}")
  public SysMenuTypeDto get(@PathVariable String id) {
    return menuTypeService.getById(id);
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @ApiOperation("4.修改状态")
  @ApiOperationSupport(order = 4)
  @PostMapping("/editStatusById/{id}")
  public Integer editStatusById(@PathVariable String id, @RequestParam("status") String status) {
    return menuTypeService.editStatusById(id, status);
  }

  /**
   * 判断是否存在
   *
   * @param checkCodeVo 查询参数
   * @return Long
   */
  @ApiOperation("5.判断是否存在")
  @ApiOperationSupport(order = 5)
  @PostMapping("/checkCode")
  public Long checkCode(@RequestBody CheckCodeVo checkCodeVo) {
    if (checkCodeVo != null) {
      return menuTypeService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 保存
   *
   * @param sysMenuTypeVo 菜单类型对象
   * @return ResponseResult<SysMenuTypeDto>
   */
  @ApiOperation("6.保存")
  @ApiOperationSupport(order = 6)
  @PutMapping("/save")
  public ResponseResult<SysMenuTypeDto> save(@RequestBody SysMenuTypeVo sysMenuTypeVo) {
    if (sysMenuTypeVo != null) {
      SysMenuTypeDto entity = menuTypeService.save(sysMenuTypeVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return List<Integer>
   */
  @ApiOperation("7.删除")
  @ApiOperationSupport(order = 7)
  @DeleteMapping("/delete")
  public List<Integer> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> menuTypeService.deleteById(id)).collect(Collectors.toList());
  }
}
