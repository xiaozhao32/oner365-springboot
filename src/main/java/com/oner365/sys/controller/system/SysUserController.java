package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.auth.AuthUser;
import com.oner365.data.commons.auth.annotation.CurrentUser;
import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.util.Md5Util;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.AttributeBean;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.data.web.utils.RequestUtils;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.log.annotation.SysLog;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.SysUserDto;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.ModifyPasswordVo;
import com.oner365.sys.vo.ResetPasswordVo;
import com.oner365.sys.vo.SysUserInfoVo;
import com.oner365.sys.vo.SysUserVo;
import com.oner365.sys.vo.check.CheckUserNameVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

/**
 * 用户管理
 *
 * @author zhaoyong
 */
@RestController
@Tag(name = "系统管理 - 用户")
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

  @Resource
  private ISysUserService sysUserService;

  @Resource
  private ISysRoleService sysRoleService;

  @Resource
  private ISysJobService sysJobService;

  @Resource
  private IFileStorageClient fileStorageClient;

  /**
   * 用户列表
   *
   * @param data 查询参数
   * @return PageInfo<SysUserDto>
   */
  @Operation(summary = "1.用户列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysUserDto> pageList(@RequestBody QueryCriteriaBean data) {
    return sysUserService.pageList(data);
  }

  /**
   * 获取信息
   *
   * @param id 编号
   * @return SysUserInfoVo
   */
  @Operation(summary = "2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public SysUserInfoVo get(@PathVariable String id) {
    SysUserDto sysUser = sysUserService.getById(id);

    SysUserInfoVo result = new SysUserInfoVo();
    result.setSysUser(sysUser);

    QueryCriteriaBean data = new QueryCriteriaBean();
    List<AttributeBean> whereList = new ArrayList<>();
    AttributeBean attribute = new AttributeBean(SysConstants.STATUS, StatusEnum.YES);
    whereList.add(attribute);
    data.setWhereList(whereList);
    result.setRoleList(sysRoleService.findList(data));
    result.setJobList(sysJobService.findList(data));

    return result;
  }

  /**
   * 个人信息
   *
   * @param authUser 登录对象
   * @return SysUserDto
   */
  @Operation(summary = "3.个人信息")
  @ApiOperationSupport(order = 3)
  @GetMapping("/profile")
  public SysUserDto profile(@Parameter(hidden = true) @CurrentUser AuthUser authUser) {
    return sysUserService.getById(authUser.getId());
  }

  /**
   * 上传图片
   *
   * @param authUser 登录对象
   * @param file     文件
   * @return String
   */
  @Operation(summary = "4.上传头像")
  @ApiOperationSupport(order = 4)
  @SysLog("上传用户头像")
  @PostMapping("/avatar")
  public String avatar(@Parameter(hidden = true) @CurrentUser AuthUser authUser, @RequestParam("avatarfile") MultipartFile file) {
    if (!file.isEmpty()) {
      String fileUrl = fileStorageClient.uploadFile(file, "avatar");
      SysUserDto sysUserDto =sysUserService.updateAvatar(authUser.getId(), fileUrl);
      return sysUserDto.getAvatar();
    }
    return null;
  }
  
  /**
   * 更新个人信息
   *
   * @param authUser  登录对象
   * @param sysUserVo 对象
   * @return SysUserDto
   */
  @Operation(summary = "5.更新个人信息")
  @ApiOperationSupport(order = 5)
  @SysLog("更新用户信息")
  @PostMapping("/update/profile")
  public SysUserDto updateUserProfile(@Parameter(hidden = true) @CurrentUser AuthUser authUser,
      @RequestBody SysUserVo sysUserVo) {
    sysUserVo.setId(authUser.getId());
    return sysUserService.updateUserProfile(sysUserVo);
  }

  /**
   * 判断用户是否存在
   *
   * @param checkUserNameVo 查询参数
   * @return Boolean
   */
  @Operation(summary = "6.判断是否存在")
  @ApiOperationSupport(order = 6)
  @PostMapping("/check")
  public Boolean checkUserName(@Validated @RequestBody CheckUserNameVo checkUserNameVo) {
    if (checkUserNameVo != null) {
      return sysUserService.checkUserName(checkUserNameVo.getId(), checkUserNameVo.getUserName());
    }
    return Boolean.FALSE;
  }

  /**
   * 重置密码
   *
   * @param resetPasswordVo 查询参数
   * @return Boolean
   */
  @Operation(summary = "7.重置密码")
  @ApiOperationSupport(order = 7)
  @SysLog("重置密码")
  @PostMapping("/reset")
  public Boolean resetPassword(@Validated @RequestBody ResetPasswordVo resetPasswordVo) {
    if (resetPasswordVo != null) {
      return sysUserService.editPassword(resetPasswordVo.getUserId(), resetPasswordVo.getPassword());
    }
    return Boolean.FALSE;
  }

  /**
   * 修改密码
   *
   * @param authUser         登录对象
   * @param modifyPasswordVo 请求参数
   * @return Boolean
   */
  @Operation(summary = "8.修改密码")
  @ApiOperationSupport(order = 8)
  @SysLog("修改密码")
  @PostMapping("/update/password")
  public String editPassword(@Parameter(hidden = true) @CurrentUser AuthUser authUser,
      @Validated @RequestBody ModifyPasswordVo modifyPasswordVo) {
    String oldPassword = Md5Util.getInstance().getMd5(modifyPasswordVo.getOldPassword()).toUpperCase();
    SysUserDto sysUser = sysUserService.getById(authUser.getId());

    if (!oldPassword.equals(sysUser.getPassword())) {
      return ErrorInfoEnum.PASSWORD_ERROR.getName();
    }
    Boolean result = sysUserService.editPassword(authUser.getId(), modifyPasswordVo.getPassword());
    return String.valueOf(result);
  }

  /**
   * 修改用户状态
   *
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  @Operation(summary = "9.修改状态")
  @ApiOperationSupport(order = 9)
  @SysLog("修改用户状态")
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysUserService.editStatus(id, status);
  }

  /**
   * 用户保存
   *
   * @param sysUserVo 用户对象
   * @return SysUserDto
   */
  @Operation(summary = "10.保存")
  @ApiOperationSupport(order = 10)
  @SysLog("保存用户")
  @PutMapping("/save")
  public SysUserDto save(@Validated @RequestBody SysUserVo sysUserVo) {
    sysUserVo.setLastIp(HttpClientUtils.getIpAddress(RequestUtils.getHttpRequest()));
    return sysUserService.saveUser(sysUserVo);
  }

  /**
   * 删除用户
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @Operation(summary = "11.删除")
  @ApiOperationSupport(order = 11)
  @SysLog("删除用户")
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysUserService.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * 导出Excel
   *
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @Operation(summary = "12.导出")
  @ApiOperationSupport(order = 12)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysUserDto> list = sysUserService.findList(data);

    String[] titleKeys = new String[] { "编号", "用户标识", "用户名称", "姓名", "性别", "邮箱", "电话", "备注", "状态", "创建时间", "最后登录时间",
        "最后登录ip" };
    String[] columnNames = { "id", "userCode", "userName", "realName", "sex", "email", "phone", "remark", "status",
        "createTime", "lastTime", "lastIp" };

    String fileName = SysUserDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}
