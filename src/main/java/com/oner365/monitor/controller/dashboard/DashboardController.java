package com.oner365.monitor.controller.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.web.controller.BaseController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 首页信息
 * 
 * @author zhaoyong
 *
 */
@RestController
@Tag(name = "监控 - 门户信息")
@RequestMapping("/monitor/dashboard")
public class DashboardController extends BaseController {

  /**
   * 首页信息
   * 
   * @return String
   */
  @Operation(summary = "1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public String index() {
    return ResultEnum.SUCCESS.getName();
  }
}
