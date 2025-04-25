package com.oner365.gateway.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.gateway.vo.GatewayRouteVo;
import com.oner365.log.annotation.SysLog;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 动态路由控制
 *
 * @author liutao
 *
 */
@RestController
@Tag(name = "动态路由控制")
@RequestMapping("/route")
public class DynamicRouteController extends BaseController {

  @Resource
  private DynamicRouteService dynamicRouteService;

  /**
   * 路由列表
   *
   * @param data 查询参数
   * @return PageInfo<GatewayRouteDto>
   */
  @Operation(summary = "1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<GatewayRouteDto> pageList(@RequestBody QueryCriteriaBean data) {
    return dynamicRouteService.pageList(data);
  }

  /**
   * 获取路由
   *
   * @param id 编号
   * @return GatewayRouteDto
   */
  @Operation(summary = "2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public GatewayRouteDto get(@PathVariable String id) {
    return dynamicRouteService.getById(id);
  }

  /**
   * 增加路由
   *
   * @param gatewayRouteVo 路由对象
   * @return GatewayRouteDto
   */
  @Operation(summary = "3.添加路由")
  @ApiOperationSupport(order = 3)
  @SysLog("添加路由")
  @PostMapping("/add")
  public GatewayRouteDto add(@Validated @RequestBody GatewayRouteVo gatewayRouteVo) {
    return dynamicRouteService.save(gatewayRouteVo);
  }

  /**
   * 刷新路由配置
   *
   * @return List<GatewayRouteDto>
   */
  @Operation(summary = "4.刷新路由")
  @ApiOperationSupport(order = 4)
  @GetMapping("/refresh")
  public List<GatewayRouteDto> refresh() {
    return dynamicRouteService.refreshRoute();
  }

  /**
   * 更新路由
   *
   * @param gatewayRouteVo 路由对象
   * @return GatewayRouteDto
   */
  @Operation(summary = "5.更新路由")
  @ApiOperationSupport(order = 5)
  @SysLog("更新路由")
  @PostMapping("/update")
  public GatewayRouteDto update(@Validated @RequestBody GatewayRouteVo gatewayRouteVo) {
    return dynamicRouteService.update(gatewayRouteVo);
  }

  /**
   * 更新路由状态
   *
   * @param id     编号
   * @param status 状态
   * @return Boolean
   */
  @Operation(summary = "6.更新状态")
  @ApiOperationSupport(order = 6)
  @SysLog("添加路由状态")
  @GetMapping("/status/{id}/{status}")
  public Boolean editStatus(@PathVariable String id, @PathVariable StatusEnum status) {
    return dynamicRouteService.editStatus(id, status);
  }

  /**
   * 删除路由
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @Operation(summary = "7.删除路由")
  @ApiOperationSupport(order = 7)
  @SysLog("删除路由")
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> dynamicRouteService.deleteById(id)).collect(Collectors.toList());
  }

}
