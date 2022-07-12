package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysMenuOperationDto;
import com.oner365.sys.service.ISysMenuOperationService;
import com.oner365.sys.vo.SysMenuOperationVo;
import com.oner365.sys.vo.check.CheckCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 菜单操作权限
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "系统管理 - 菜单操作权限")
@RequestMapping("/system/menu/operation")
public class SysMenuOperationController extends BaseController {

  @Autowired
  private ISysMenuOperationService menuOperationService;

  /**
   * 列表
   *
   * @param data 查询参数
   * @return PageInfo<SysMenuOperationDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysMenuOperationDto> pageList(@RequestBody QueryCriteriaBean data) {
    return menuOperationService.pageList(data);
  }

  /**
   * 获取信息
   *
   * @param id 编号
   * @return SysMenuOperationDto
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public SysMenuOperationDto getById(@PathVariable String id) {
    return menuOperationService.getById(id);
  }
  
  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  @ApiOperation("3.修改状态")
  @ApiOperationSupport(order = 3)
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return menuOperationService.editStatus(id, status);
  }

  /**
   * 判断是否存在
   *
   * @param checkCodeVo 查询参数
   * @return Boolean
   */
  @ApiOperation("4.判断是否存在")
  @ApiOperationSupport(order = 4)
  @PostMapping("/check")
  public Boolean checkCode(@Validated @RequestBody CheckCodeVo checkCodeVo) {
    if (checkCodeVo != null) {
      return menuOperationService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
    }
    return Boolean.FALSE;
  }

  /**
   * 保存
   *
   * @param sysMenuOperationVo 操作对象
   * @return ResponseResult<SysMenuOperationDto>
   */
  @ApiOperation("5.保存")
  @ApiOperationSupport(order = 5)
  @PutMapping("/save")
  public ResponseResult<SysMenuOperationDto> save(@Validated @RequestBody SysMenuOperationVo sysMenuOperationVo) {
    if (sysMenuOperationVo != null) {
      SysMenuOperationDto entity = menuOperationService.save(sysMenuOperationVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @ApiOperation("6.删除")
  @ApiOperationSupport(order = 6)
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> menuOperationService.deleteById(id)).collect(Collectors.toList());
  }
  
  /**
   * 导出Excel
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("7.导出")
  @ApiOperationSupport(order = 7)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysMenuOperationDto> list = menuOperationService.findList(data);

    String[] titleKeys = new String[] { "编号", "操作名称", "操作类型", "状态", "创建时间", "更新时间" };
    String[] columnNames = { "id", "operationName", "operationType", "status", "createTime", "updateTime" };

    String fileName = SysMenuOperationDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}
