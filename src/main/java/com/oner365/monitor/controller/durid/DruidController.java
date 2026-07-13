package com.oner365.monitor.controller.durid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oner365.data.commons.config.properties.CommonProperties;
import com.oner365.data.web.controller.BaseController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * druid 监控
 *
 * @author zhaoyong
 *
 */
@Controller
@RequestMapping("/monitor/druid")
public class DruidController extends BaseController {

    @Resource
    private CommonProperties commonProperties;

    /**
     * 监控首页
     * 
     * @return String
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + commonProperties.getPort()
                + request.getContextPath();
        String redirectUrl = baseUrl + "/druid/index.html";
        return "redirect:" + redirectUrl;
    }

}
