package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.oner365.data.commons.auth.AuthUser;
import com.oner365.data.commons.auth.annotation.CurrentUser;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.sys.dto.SysMenuTreeSelectDto;
import com.oner365.sys.dto.SysOrganizationDto;
import com.oner365.sys.dto.TreeSelect;
import com.oner365.sys.service.ISysOrganizationService;
import com.oner365.sys.vo.SysOrganizationVo;
import com.oner365.sys.vo.check.CheckOrgCodeVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

/**
 * 机构管理
 *
 * @author zhaoyong
 */
@RestController
@Tag(name = "系统管理 - 机构")
@RequestMapping("/system/org")
public class SysOrganizationController extends BaseController {

  @Resource
  private ISysOrganizationService sysOrgService;

  /**
   * 获取列表
   *
   * @param data 查询对象
   * @return List<SysOrganizationDto>
   */
  @Operation(summary = "1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public List<SysOrganizationDto> findList(@RequestBody QueryCriteriaBean data) {
    return sysOrgService.findList(data);
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return SysOrganizationDto
   */
  @Operation(summary = "2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public SysOrganizationDto get(@PathVariable String id) {
    return sysOrgService.getById(id);
  }

  /**
   * 直接测试数据源是否连接
   *
   * @param ds       数据源类型
   * @param driverName 驱动名称
   * @param url        ip地址
   * @param username   账号
   * @param password   密码
   * @return boolean
   */
  @PostMapping("/connection/{ds}")
  public boolean isConnection(@PathVariable String ds, @RequestParam String driverName,
      @RequestParam String url, @RequestParam String username, @RequestParam String password) {
    return sysOrgService.isConnection(driverName, url, username, password);
  }

  /**
   * 判断保存后数据源是否连接
   *
   * @param id 编号
   * @return boolean
   */
  @Operation(summary = "4.测试连接数据源")
  @ApiOperationSupport(order = 4)
  @GetMapping("/connection/check/{id}")
  public boolean checkConnection(@PathVariable String id) {
    return sysOrgService.checkConnection(id);
  }

  /**
   * 按父级菜单查询
   *
   * @param parentId 父级编号
   * @return List<SysOrganizationDto>
   */
  @Operation(summary = "5.父级id查询")
  @ApiOperationSupport(order = 5)
  @GetMapping("/parent")
  public List<SysOrganizationDto> findListByParentId(@RequestParam("parentId") String parentId) {
    return sysOrgService.findListByParentId(parentId);
  }

  /**
   * 判断机构编号是否存在
   *
   * @param checkOrgCodeVo 查询参数
   * @return Boolean
   */
  @Operation(summary = "6.判断编码是否存在")
  @ApiOperationSupport(order = 6)
  @PostMapping("/check")
  public Boolean checkCode(@Validated @RequestBody CheckOrgCodeVo checkOrgCodeVo) {
    if (checkOrgCodeVo != null) {
      return sysOrgService.checkCode(checkOrgCodeVo.getId(), checkOrgCodeVo.getCode(), checkOrgCodeVo.getType());
    }
    return Boolean.FALSE;
  }

  /**
   * 获取菜单下拉树列表
   *
   * @param sysOrganizationVo 查询对象
   * @return List<TreeSelect>
   */
  @Operation(summary = "7.获取树型列表")
  @ApiOperationSupport(order = 7)
  @PostMapping("/tree")
  public List<TreeSelect> treeSelect(@RequestBody SysOrganizationVo sysOrganizationVo) {
    List<SysOrganizationDto> list = sysOrgService.selectList(sysOrganizationVo);
    return sysOrgService.buildTreeSelect(list);
  }

  /**
   * 加载对应角色菜单列表树
   *
   * @param sysOrganizationVo 查询对象
   * @param userId            用户id
   * @return SysMenuTreeSelectDto
   */
  @Operation(summary = "8.获取用户权限")
  @ApiOperationSupport(order = 8)
  @PostMapping("/user/{userId}")
  public SysMenuTreeSelectDto userTreeSelect(@RequestBody SysOrganizationVo sysOrganizationVo,
      @PathVariable("userId") String userId) {
    List<SysOrganizationDto> list = sysOrgService.selectList(sysOrganizationVo);
    SysMenuTreeSelectDto result = new SysMenuTreeSelectDto();

    result.setCheckedKeys(sysOrgService.selectListByUserId(userId));
    result.setMenus(sysOrgService.buildTreeSelect(list));
    return result;
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  @Operation(summary = "9.修改状态")
  @ApiOperationSupport(order = 9)
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysOrgService.editStatus(id, status);
  }

  /**
   * 机构信息保存
   *
   * @param sysOrganizationVo 机构对象
   * @param authUser          登录对象
   * @return SysOrganizationDto
   */
  @Operation(summary = "10.保存")
  @ApiOperationSupport(order = 10)
  @PutMapping("/save")
  public SysOrganizationDto save(@Validated @RequestBody SysOrganizationVo sysOrganizationVo,
      @Parameter(hidden = true) @CurrentUser AuthUser authUser) {
    sysOrganizationVo.setCreateUser(authUser.getId());
    return sysOrgService.save(sysOrganizationVo);
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @Operation(summary = "11.删除")
  @ApiOperationSupport(order = 11)
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysOrgService.deleteById(id)).collect(Collectors.toList());
  }

}
