package com.oner365.sys.controller.system;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.files.client.FastdfsClient;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysUser;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.SysUserVo;
import com.oner365.util.DataUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户管理
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "系统管理 - 用户")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysJobService sysJobService;
    
    @Autowired
    private FastdfsClient fastdfsClient;

    /**
     * 用户列表
     *
     * @param paramJson 参数
     * @return Page<SysUser>
     */
    @PostMapping("/list")
    @ApiOperation("用户列表")
    public Page<SysUser> findUserList(@RequestBody JSONObject paramJson) {
        return sysUserService.pageList(paramJson);
    }

    /**
     * 用户保存
     *
     * @param sysUserVo 用户对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    @ApiOperation("保存")
    public Map<String, Object> save(@RequestBody SysUserVo sysUserVo, HttpServletRequest request) {
        SysUser sysUser = sysUserVo.toObject();
        sysUser.setLastIp(DataUtils.getIpAddress(request));
        SysUser entity = sysUserService.saveUser(sysUser);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
        result.put(PublicConstants.MSG, entity);
        return result;
    }

    /**
     * 获取信息
     *
     * @param id 编号
     * @return Map<String, Object>
     */
    @GetMapping("/get/{id}")
    @ApiOperation("按id查询")
    public Map<String, Object> get(@PathVariable String id) {
        SysUser sysUser = sysUserService.getById(id);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.MSG, sysUser);

        result.put("roleList", sysRoleService.findList(new JSONObject()));
        result.put("jobList", sysJobService.findList(new JSONObject()));
        return result;
    }

    /**
     * 个人信息
     * 
     * @param authUser 登录对象
     * @return SysUser
     */
    @GetMapping("/profile")
    @ApiOperation("个人信息")
    public SysUser profile(@CurrentUser AuthUser authUser) {
        return sysUserService.getById(authUser.getId());
    }

    /**
     * 上传图片
     * 
     * @param authUser 登录对象
     * @param file     文件
     * @return String
     */
    @PostMapping("/avatar")
    @ApiOperation("上传头像")
    public String avatar(@CurrentUser AuthUser authUser, @RequestParam("avatarfile") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileUrl = fastdfsClient.uploadFile(file);

            SysUser sysUser = sysUserService.getById(authUser.getId());
            sysUser.setAvatar(fileUrl);
            sysUserService.update(sysUser);
            return fileUrl;
        }
        return "";
    }

    /**
     * 更新个人信息
     * 
     * @param sysUserVo 对象
     * @param authUser  登录对象
     * @return ResponseData
     */
    @PostMapping("/updateUserProfile")
    @ApiOperation("更新个人信息")
    public SysUser updateUserProfile(@RequestBody SysUserVo sysUserVo, @CurrentUser AuthUser authUser) {
        SysUser vo = sysUserVo.toObject();
        if (vo != null) {
            SysUser sysUser = sysUserService.getById(authUser.getId());
            sysUser.setEmail(vo.getEmail());
            sysUser.setRealName(vo.getRealName());
            sysUser.setPhone(vo.getPhone());
            sysUser.setSex(vo.getSex());
            sysUser.setLastTime(new Timestamp(System.currentTimeMillis()));
            return sysUserService.update(sysUser);
        }
        return null;
    }

    /**
     * 删除用户
     *
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Integer delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = sysUserService.deleteById(id);
        }
        return code;
    }

    /**
     * 判断用户是否存在
     *
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkUserName")
    @ApiOperation("判断存在")
    public Map<String, Object> checkUserName(@RequestBody JSONObject json) {
        String userName = json.getString(SysConstants.USER_NAME);
        String userId = json.getString(SysConstants.ID);
        long code = sysUserService.checkUserName(userId, userName);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 修改密码
     *
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/resetPassword")
    @ApiOperation("重置密码")
    public Map<String, Object> resetPassword(@RequestBody JSONObject json) {
        String userId = json.getString(SysConstants.USER_ID);
        String password = json.getString(SysConstants.P);
        Integer code = sysUserService.editPassword(userId, password);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 修改密码
     *
     * @param authUser 登录对象
     * @param json     参数
     * @return Map<String, Object>
     */
    @PostMapping("/editPassword")
    @ApiOperation("修改密码")
    public Map<String, Object> editPassword(@CurrentUser AuthUser authUser, @RequestBody JSONObject json) {
        String oldPassword = DigestUtils.md5Hex(json.getString("oldPassword")).toUpperCase();
        String password = json.getString(SysConstants.P);
        SysUser sysUser = sysUserService.getById(authUser.getId());
        
        Map<String, Object> result = Maps.newHashMap();
        if (!oldPassword.equals(sysUser.getPassword())) {
            result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
            return result;
        }
        Integer code = sysUserService.editPassword(authUser.getId(), password);
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 修改用户状态
     *
     * @param json 参数
     * @return Integer
     */
    @PostMapping("/editStatus")
    @ApiOperation("修改状态")
    public Integer editStatus(@RequestBody JSONObject json) {
        String status = json.getString(SysConstants.STATUS);
        String userId = json.getString(SysConstants.USER_ID);
        return sysUserService.editStatus(userId, status);
    }

    /**
     * 导出Excel
     * 
     * @param paramJson 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/export")
    @ApiOperation("导出")
    public ResponseEntity<byte[]> export(@RequestBody JSONObject paramJson) {
        List<SysUser> list = sysUserService.findList(paramJson);

        String[] titleKeys = new String[] { "编号", "用户标识", "用户名称", "姓名", "性别", "邮箱", "电话", "备注", "状态", "创建时间", "最后登录时间",
                "最后登录ip" };
        String[] columnNames = { "id", "userCode", "userName", "realName", "sex", "email", "phone", "remark", "status",
                "createTime", "lastTime", "lastIp" };

        String fileName = SysUser.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
