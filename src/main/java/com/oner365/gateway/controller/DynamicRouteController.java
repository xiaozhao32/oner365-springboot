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
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.gateway.vo.GatewayRouteVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 动态路由控制
 *
 * @author liutao
 *
 */
@RestController
@Api(tags = "动态路由控制")
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
  @ApiOperation("1.获取列表")
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
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public GatewayRouteDto get(@PathVariable String id) {
    return dynamicRouteService.getById(id);
  }

  /**
   * 增加路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseResult<GatewayRouteDto>
   */
  @ApiOperation("3.添加路由")
  @ApiOperationSupport(order = 3)
  @PostMapping("/add")
  public ResponseResult<GatewayRouteDto> add(@Validated @RequestBody GatewayRouteVo gatewayRouteVo) {
    if (gatewayRouteVo != null) {
      GatewayRouteDto result = dynamicRouteService.save(gatewayRouteVo);
      return ResponseResult.success(result);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 刷新路由配置
   *
   * @return List<GatewayRouteDto>
   */
  @ApiOperation("4.刷新路由")
  @ApiOperationSupport(order = 4)
  @GetMapping("/refresh")
  public List<GatewayRouteDto> refresh() {
    return dynamicRouteService.refreshRoute();
  }

  /**
   * 更新路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseResult<GatewayRouteDto>
   */
  @ApiOperation("5.更新路由")
  @ApiOperationSupport(order = 5)
  @PostMapping("/update")
  public ResponseResult<GatewayRouteDto> update(@Validated @RequestBody GatewayRouteVo gatewayRouteVo) {
    if (gatewayRouteVo != null) {
      GatewayRouteDto result = dynamicRouteService.update(gatewayRouteVo);
      return ResponseResult.success(result);
    }
    return ResponseResult.error(ErrorInfoEnum.UPDATE_ERROR.getName());
  }

  /**
   * 更新路由状态
   *
   * @param id     编号
   * @param status 状态
   * @return ResponseResult<Boolean>
   */
  @ApiOperation("6.更新状态")
  @ApiOperationSupport(order = 6)
  @GetMapping("/status/{id}/{status}")
  public ResponseResult<Boolean> editStatus(@PathVariable String id, @PathVariable StatusEnum status) {
    Boolean result = dynamicRouteService.editStatus(id, status);
    return ResponseResult.success(result);
  }

  /**
   * 删除路由
   *
   * @param ids 编号
   * @return ResponseResult<Boolean>
   */
  @ApiOperation("7.删除路由")
  @ApiOperationSupport(order = 7)
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> dynamicRouteService.deleteById(id)).collect(Collectors.toList());
  }

}
