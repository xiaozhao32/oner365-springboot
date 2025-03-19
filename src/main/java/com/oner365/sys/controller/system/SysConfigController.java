package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.sys.dto.SysConfigDto;
import com.oner365.sys.service.ISysConfigService;
import com.oner365.sys.vo.SysConfigVo;
import com.oner365.sys.vo.check.CheckConfigNameVo;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;

/**
 * nt_sys_config Controller
 * 
 * @author zhaoyong
 */
@RestController
@Tag(name = "系统管理 - 系统配置")
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {

  @Resource
  private ISysConfigService sysConfigService;

  /**
   * 系统配置获取列表
   *
   * @param data 查询参数
   * @return PageInfo<SysConfigDto>
   */
  @Operation(summary = "1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysConfigDto> pageList(@RequestBody QueryCriteriaBean data) {
    return sysConfigService.pageList(data);
  }

  /**
   * 获取系统配置
   *
   * @param id 编号
   * @return SysConfigDto
   */
  @Operation(summary = "2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public SysConfigDto get(@PathVariable String id) {
    return sysConfigService.getById(id);
  }

  /**
   * 修改系统配置状态
   *
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  @Operation(summary = "3.修改状态")
  @ApiOperationSupport(order = 3)
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysConfigService.editStatus(id, status);
  }

  /**
   * 系统配置保存
   *
   * @param sysConfigVo 对象
   * @return SysConfigDto
   */
  @Operation(summary = "4.保存")
  @ApiOperationSupport(order = 4)
  @PutMapping("/save")
  public SysConfigDto save(@RequestBody SysConfigVo sysConfigVo) {
      return sysConfigService.save(sysConfigVo);
  }

  /**
   * 删除系统配置
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @Operation(summary = "5.删除")
  @ApiOperationSupport(order = 5)
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysConfigService.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * 导出Excel
   *
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @Operation(summary = "6.导出")
  @ApiOperationSupport(order = 6)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysConfigDto> list = sysConfigService.findList(data);

    String[] titleKeys = new String[] { "主键", "配置名称", "配置内容", "状态", "创建时间", "更新时间", };
    String[] columnNames = { "id", "configName", "configValue", "status", "createTime", "updateTime", };

    String fileName = "系统配置_" + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }
  
  /**
   * 判断是否存在
   *
   * @param checkConfigNameVo 查询参数
   * @return Boolean
   */
  @Operation(summary = "7.判断是否存在")
  @ApiOperationSupport(order = 7)
  @PostMapping("/check")
  public Boolean checkConfigName(@Validated @RequestBody CheckConfigNameVo checkConfigNameVo) {
    if (checkConfigNameVo != null) {
      return sysConfigService.checkConfigName(checkConfigNameVo.getId(), checkConfigNameVo.getConfigName());
    }
    return Boolean.FALSE;
  }

}
