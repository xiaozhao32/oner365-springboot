package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysRoleDto;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.vo.SysRoleVo;
import com.oner365.sys.vo.check.CheckRoleNameVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色管理
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 角色")
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

  @Autowired
  private ISysRoleService roleService;

  /**
   * 列表
   *
   * @param data 查询参数
   * @return PageInfo<SysRoleDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysRoleDto> list(@RequestBody QueryCriteriaBean data) {
    return roleService.pageList(data);
  }

  /**
   * 获取信息
   *
   * @param id 编号
   * @return SysRoleDto
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public SysRoleDto get(@PathVariable String id) {
    return roleService.getById(id);
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @ApiOperation("3.修改状态")
  @ApiOperationSupport(order = 3)
  @PostMapping("/editStatus/{id}")
  public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
    return roleService.editStatus(id, status);
  }

  /**
   * 判断是否存在
   *
   * @param checkRoleNameVo 查询参数
   * @return Long
   */
  @ApiOperation("4.判断角色名称存在")
  @ApiOperationSupport(order = 4)
  @PostMapping("/checkRoleName")
  public Long checkRoleName(@RequestBody CheckRoleNameVo checkRoleNameVo) {
    if (checkRoleNameVo != null) {
      return roleService.checkRoleName(checkRoleNameVo.getId(), checkRoleNameVo.getRoleName());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 角色权限保存
   *
   * @param sysRoleVo 参数
   * @return ResponseResult<Integer>
   */
  @ApiOperation("5.保存")
  @ApiOperationSupport(order = 5)
  @PutMapping("/save")
  public ResponseResult<Integer> save(@RequestBody SysRoleVo sysRoleVo) {
    if (sysRoleVo != null) {
      // 保存角色
      SysRoleDto entity = roleService.save(sysRoleVo);
      if (entity != null) {
        // 保存权限
        int code = roleService.saveAuthority(sysRoleVo.getMenuType(), sysRoleVo.getMenuIds(), entity.getId());
        return ResponseResult.success(code);
      }
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return List<Integer>
   */
  @ApiOperation("6.删除")
  @ApiOperationSupport(order = 6)
  @DeleteMapping("/delete")
  public List<Integer> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> roleService.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * 导出Excel
   *
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("7.导出")
  @ApiOperationSupport(order = 7)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysRoleDto> list = roleService.findList(data);

    String[] titleKeys = new String[] { "编号", "角色标识", "角色名称", "角色描述", "状态", "创建时间", "更新时间" };
    String[] columnNames = { "id", "roleCode", "roleName", "roleDes", "status", "createTime", "updateTime" };

    String fileName = SysRoleDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}
