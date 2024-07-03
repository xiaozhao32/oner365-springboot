package com.oner365.client.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.web.controller.BaseController;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.vo.LoginUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 测试加密body
 *
 * @author liutao
 */
@RestController
@Api(tags = "加密传输body测试")
@RequestMapping("/client")
public class ClientTestController extends BaseController {

  /**
   * 测试系统登录
   *
   * @param loginUserVo 登录对象
   * @return ResponseData
   */
  @PostMapping("/login")
  @ApiOperation("1.登录")
  @ApiOperationSupport(order = 1)
  public ResponseData<LoginUserDto> login(@RequestBody LoginUserVo loginUserVo) {
    if (logger.isDebugEnabled()) {
      logger.debug("result:{}", JSON.toJSONString(loginUserVo));
    }
    LoginUserDto dto = new LoginUserDto();
    dto.setRealName("成功了");
    return ResponseData.success(dto);
  }

}
