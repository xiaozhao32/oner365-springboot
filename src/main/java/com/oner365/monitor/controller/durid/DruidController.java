package com.oner365.monitor.controller.durid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oner365.controller.BaseController;

/**
 * druid 监控
 * 
 * @author zhaoyong
 *
 */
@Controller
@RequestMapping("/monitor/druid")
public class DruidController extends BaseController {

  /**
   * 监控首页
   * 
   * @return String
   */
  @GetMapping("/index")
  public String index() {
    return "redirect:/druid/index.html";
  }
}
