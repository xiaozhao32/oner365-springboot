package com.oner365.sys.controller.auth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.oner365.common.ResponseData;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.cache.RedisCache;
import com.oner365.common.constants.ErrorCodes;
import com.oner365.common.constants.ErrorInfo;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.LoginUserVo;
import com.oner365.util.DataUtils;
import com.oner365.util.VerifyCodeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 认证登录接口
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/auth")
@Api(tags = "用户认证")
public class AuthController extends BaseController {

    private static final String CACHE_LOGIN_NAME = "Auth:Login";
    
    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private RedisCache redisCache;
    
    /**
     * 系统登录
     *
     * @param loginUserVo 登录对象
     * @return ResponseData
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseData<LoginUserDto> login(HttpServletRequest request, @RequestBody LoginUserVo loginUserVo) {
        // 验证码
        if (!DataUtils.isEmpty(loginUserVo.getUuid())) {
            String verifyKey = SysConstants.CAPTCHA_IMAGE + ":" + loginUserVo.getUuid();
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null || !captcha.equalsIgnoreCase(loginUserVo.getCode())) {
                return ResponseData.error(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_CAPTCHA_ERROR);
            }
        }
        
        // 验证参数
        String userName = loginUserVo.getUserName();
        if (Strings.isNullOrEmpty(userName)) {
            return ResponseData.error(ErrorCodes.ERR_USER_NAME_NOT_NULL, ErrorInfo.ERR_USER_NAME_NOT_NULL);
        }
        String password = loginUserVo.getPassword();
        if (Strings.isNullOrEmpty(password)) {
            return ResponseData.error(ErrorCodes.ERR_PASSWORD_NOT_NULL, ErrorInfo.ERR_PASS_NOT_NULL);
        }
        // ip地址
        String ip = DataUtils.getIpAddress(request);
        LOGGER.info("ip: {}", ip);

        // 登录
        LoginUserDto result = sysUserService.login(userName, password);
        
        // 返回结果
        if (result != null) {
            return ResponseData.success(result);
        }
        return new ResponseData<>(ErrorCodes.ERR_USER_NOT_FOUND, ErrorInfo.ERR_USER_NOT_FOUND);
    }

    /**
     * 退出登录
     * @return String
     */
    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public String logout(@CurrentUser AuthUser authUser) {
        String key = CACHE_LOGIN_NAME + ":" + authUser.getUserName();
        redisCache.deleteObject(key);
        return PublicConstants.SUCCESS;
    }

    /**
     * 获取左侧菜单
     *
     * @param menuType 菜单类型
     * @return JSONArray
     */
    @GetMapping("/menu/{menuType}")
    @ApiOperation("获取菜单权限")
    public JSONArray findMenuByRoles(@CurrentUser AuthUser user, @PathVariable String menuType) {
        try {
            if (user != null && !user.getRoleList().isEmpty()) {
                return sysRoleService.findMenuByRoles(user.getRoleList(), menuType);
            }
        } catch (Exception e) {
            LOGGER.error("Error findMenuByRoles: ", e);
        }
        return new JSONArray();
    }

    /**
     * 获取验证码
     * @return Map<String, Object>
     */
    @GetMapping("/captchaImage")
    @ApiOperation("获取验证码")
    public Map<String, Object> captchaImage() {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 唯一标识
        String uuid = UUID.randomUUID().toString();
        String verifyKey = SysConstants.CAPTCHA_IMAGE + ":" + uuid;
        redisCache.setCacheObject(verifyKey, verifyCode, 3, TimeUnit.MINUTES);

        Map<String, Object> result = Maps.newHashMap();
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            // 生成图片
            int w = 111;
            int h = 36;
            VerifyCodeUtils.outputImage(w, h, stream, verifyCode);

            result.put(SysConstants.UUID, uuid);
            result.put("img", Base64Utils.encodeToString(stream.toByteArray()));
        } catch (IOException e) {
            LOGGER.error("Error captchaImage: ", e);
        }

        return result;
    }

    /**
     * 获取菜单对应权限
     *
     * @param menuId 菜单id
     * @return List<Map<String, Object>>
     */
    @GetMapping("/menu/operation/{menuId}")
    @ApiOperation("获取菜单操作权限")
    public List<Map<String, String>> findMenuOperByRoles(@CurrentUser AuthUser user, @PathVariable String menuId) {
        try {
            if (user != null && !user.getRoleList().isEmpty()) {
                return sysRoleService.findMenuOperByRoles(user.getRoleList(), menuId);
            }
        } catch (Exception e) {
            LOGGER.error("Error findMenuOperByRoles: ", e);
        }
        return new ArrayList<>();
    }

    

}
