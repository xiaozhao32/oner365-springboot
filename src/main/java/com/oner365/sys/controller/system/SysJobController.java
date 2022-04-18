package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysJobDto;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.vo.SysJobVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户职位信息
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 用户职位")
@RequestMapping("/system/job")
public class SysJobController extends BaseController {

  @Autowired
  private ISysJobService sysJobService;

  /**
   * 用户职位列表
   *
   * @param data 查询参数
   * @return Page<SysJobDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysJobDto> pageList(@RequestBody QueryCriteriaBean data) {
    return sysJobService.pageList(data);
  }

  /**
   * 获取用户职位
   *
   * @param id 编号
   * @return SysJobDto
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public SysJobDto get(@PathVariable String id) {
    return sysJobService.getById(id);
  }

  /**
   * 修改职位状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @ApiOperation("3.修改状态")
  @ApiOperationSupport(order = 3)
  @PostMapping("/status/{id}")
  public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
    return sysJobService.editStatus(id, status);
  }

  /**
   * 用户职位保存
   *
   * @param sysJobVo 职位对象
   * @return ResponseResult<SysJobDto>
   */
  @ApiOperation("4.保存")
  @ApiOperationSupport(order = 4)
  @PutMapping("/save")
  public ResponseResult<SysJobDto> save(@RequestBody SysJobVo sysJobVo) {
    if (sysJobVo != null) {
      SysJobDto entity = sysJobService.save(sysJobVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除用户职位
   *
   * @param ids 编号
   * @return List<Integer>
   */
  @ApiOperation("5.删除")
  @ApiOperationSupport(order = 5)
  @DeleteMapping("/delete")
  public List<Integer> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysJobService.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * 导出Excel
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("6.导出")
  @ApiOperationSupport(order = 6)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysJobDto> list = sysJobService.findList(data);

    String[] titleKeys = new String[] { "编号", "职位名称", "职位描述", "排序", "状态", "创建时间", "更新时间" };
    String[] columnNames = { "id", "jobName", "jobInfo", "jobOrder", "status", "createTime", "updateTime" };

    String fileName = SysJobDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}
