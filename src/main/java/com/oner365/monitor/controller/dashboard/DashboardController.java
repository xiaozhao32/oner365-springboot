package com.oner365.monitor.controller.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 首页信息
 * 
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "监控 - 门户信息")
@RequestMapping("/monitor/dashboard")
public class DashboardController extends BaseController {

  /**
   * 首页信息
   * 
   * @return String
   */
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public String index() {
    return ResultEnum.SUCCESS.getName();
  }
}
