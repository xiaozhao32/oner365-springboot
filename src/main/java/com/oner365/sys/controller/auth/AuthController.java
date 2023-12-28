package com.oner365.sys.controller.auth;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.code.kaptcha.Producer;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.cache.RedisCache;
import com.oner365.common.cache.constants.CacheConstants;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.CaptchaImageDto;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.dto.SysMenuOperDto;
import com.oner365.sys.dto.SysMenuTreeDto;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.LoginUserVo;
import com.oner365.util.DataUtils;
import com.oner365.util.RequestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 认证登录接口
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "用户认证")
@RequestMapping("/system/auth")
public class AuthController extends BaseController {

  @Resource
  private ISysUserService sysUserService;

  @Resource
  private ISysRoleService sysRoleService;

  @Resource
  private RedisCache redisCache;
  
  @Resource(name = "captchaProducer")
  private Producer producer;

  /**
   * 系统登录
   *
   * @param loginUserVo 登录对象
   * @return ResponseData<LoginUserDto>
   */
  @PostMapping("/login")
  @ApiOperation("1.登录")
  @ApiOperationSupport(order = 1)
  public ResponseData<LoginUserDto> login(@Validated @RequestBody LoginUserVo loginUserVo) {
    // 验证码
    if (!DataUtils.isEmpty(loginUserVo.getUuid())) {
      String verifyKey = SysConstants.CAPTCHA_IMAGE + PublicConstants.COLON + loginUserVo.getUuid();
      String captcha = redisCache.getCacheObject(verifyKey);
      redisCache.deleteObject(verifyKey);
      if (captcha == null || !captcha.equalsIgnoreCase(loginUserVo.getCode())) {
        return ResponseData.error(ErrorInfoEnum.CAPTCHA_ERROR.getName());
      }
    }

    // 验证参数
    String userName = loginUserVo.getUserName();
    if (DataUtils.isEmpty(userName)) {
      return ResponseData.error(ErrorInfoEnum.USER_NAME_NOT_NULL.getName());
    }
    String password = loginUserVo.getPassword();
    if (DataUtils.isEmpty(password)) {
      return ResponseData.error(ErrorInfoEnum.PASSWORD_NOT_NULL.getName());
    }
    // ip地址
    String ip = DataUtils.getIpAddress(RequestUtils.getHttpRequest());

    // 登录
    LoginUserDto result = sysUserService.login(userName, password, ip);

    // 返回结果
    if (result != null) {
      return ResponseData.success(result);
    }
    return ResponseData.error(ErrorInfoEnum.USER_NAME_NOT_NULL.getName());
  }

  /**
   * 获取验证码
   *
   * @return CaptchaImageDto
   */
  @ApiOperation("2.获取验证码")
  @ApiOperationSupport(order = 2)
  @GetMapping("/captcha")
  public CaptchaImageDto captchaImage() {
    // 生成随机字串
    String verifyCode = producer.createText();
    logger.info("login captcha:{}", verifyCode);
    // 唯一标识
    String uuid = UUID.randomUUID().toString();
    String verifyKey = SysConstants.CAPTCHA_IMAGE + PublicConstants.COLON + uuid;
    redisCache.setCacheObject(verifyKey, verifyCode, 3, TimeUnit.MINUTES);

    CaptchaImageDto result = new CaptchaImageDto();
    
    try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      BufferedImage image = producer.createImage(verifyCode);
      ImageIO.write(image, "jpg", stream);

      result.setUuid(uuid);
      result.setImg(Base64.getEncoder().encodeToString(stream.toByteArray()));
    } catch (IOException e) {
      logger.error("Error captchaImage: ", e);
    }

    return result;
  }

  /**
   * 获取左侧菜单
   *
   * @return List<SysMenuTreeDto>
   */
  @ApiOperation("3.获取菜单权限")
  @ApiOperationSupport(order = 3)
  @GetMapping("/menu")
  public List<SysMenuTreeDto> findMenuByRoles(@ApiIgnore @CurrentUser AuthUser user) {
    try {
      if (user != null && !user.getRoleList().isEmpty() && !DataUtils.isEmpty(user.getMenuType())) {
        return sysRoleService.findMenuByRoles(user.getRoleList(), user.getMenuType());
      }
    } catch (Exception e) {
      logger.error("Error findMenuByRoles: ", e);
    }
    return Collections.emptyList();
  }

  /**
   * 获取菜单对应权限
   *
   * @param menuId 菜单id
   * @return List<SysMenuOperDto>
   */
  @ApiOperation("4.获取菜单操作权限")
  @ApiOperationSupport(order = 4)
  @GetMapping("/menu/operation/{menuId}")
  public List<SysMenuOperDto> findMenuOperByRoles(@ApiIgnore @CurrentUser AuthUser user,
      @PathVariable String menuId) {
    try {
      if (user != null && !user.getRoleList().isEmpty()) {
        return sysRoleService.findMenuOperByRoles(user.getRoleList(), menuId);
      }
    } catch (Exception e) {
      logger.error("Error findMenuOperByRoles: ", e);
    }
    return Collections.emptyList();
  }

  /**
   * 退出登录
   *
   * @return ResponseResult<String>
   */
  @ApiOperation("5.退出登录")
  @ApiOperationSupport(order = 5)
  @PostMapping("/logout")
  public ResponseResult<String> logout(@ApiIgnore @CurrentUser AuthUser authUser) {
    if (authUser != null) {
      String key = CacheConstants.CACHE_LOGIN_NAME + authUser.getUserName();
      redisCache.deleteObject(key);
    }
    return ResponseResult.success(ResultEnum.SUCCESS.getName());
  }

}
