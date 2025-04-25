package com.oner365.client.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.web.controller.BaseController;
import com.oner365.log.annotation.SysLog;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.vo.LoginUserVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 测试加密body
 *
 * @author liutao
 */
@RestController
@Tag(name = "加密传输body测试")
@RequestMapping("/client")
public class ClientTestController extends BaseController {

  /**
   * 测试系统登录
   *
   * @param loginUserVo 登录对象
   * @return ResponseData
   */
  @Operation(summary = "1.登录")
  @ApiOperationSupport(order = 1)
  @SysLog("用户登录")
  @PostMapping("/login")
  public ResponseData<LoginUserDto> login(@RequestBody LoginUserVo loginUserVo) {
    if (logger.isDebugEnabled()) {
      logger.debug("result:{}", JSON.toJSONString(loginUserVo));
    }
    LoginUserDto dto = new LoginUserDto();
    dto.setRealName("成功了");
    return ResponseData.success(dto);
  }

}
