package com.oner365.generator.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.generator.entity.GenTable;
import com.oner365.generator.entity.GenTableColumn;
import com.oner365.generator.service.IGenTableColumnService;
import com.oner365.generator.service.IGenTableService;
import com.oner365.util.ConvertString;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 代码生成 操作处理
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "代码生成")
@RequestMapping("/generator/gen")
public class GeneratorController extends BaseController {

  @Autowired
  private IGenTableService genTableService;

  @Autowired
  private IGenTableColumnService genTableColumnService;

  /**
   * 查询代码生成列表
   */
  @ApiOperation("1.生成列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public Map<String, Object> genList(@RequestBody GenTable genTable) {
    List<GenTable> list = genTableService.selectGenTableList(genTable);
    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.PARAM_LIST, list);
    result.put(PublicConstants.PARAM_COUNT, list.size());
    return result;
  }

  /**
   * 查询数据库列表
   */
  @ApiOperation("2.查询数据库列表")
  @ApiOperationSupport(order = 2)
  @PostMapping("/db/list")
  public Map<String, Object> dataList(@RequestBody GenTable genTable) {
    List<GenTable> list = genTableService.selectDbTableList(genTable);
    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.PARAM_LIST, list);
    result.put(PublicConstants.PARAM_COUNT, list.size());
    return result;
  }

  /**
   * 查询数据表字段列表
   */
  @ApiOperation("3.查询字段列表")
  @ApiOperationSupport(order = 3)
  @GetMapping(value = "/column/{tableId}")
  public Map<String, Object> columnList(@PathVariable Long tableId) {
    List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.PARAM_LIST, list);
    result.put(PublicConstants.PARAM_COUNT, list.size());
    return result;
  }

  /**
   * 获取生成信息
   */
  @ApiOperation("4.获取生成信息")
  @ApiOperationSupport(order = 4)
  @GetMapping(value = "/{tableId}")
  public Map<String, Object> getInfo(@PathVariable Long tableId) {
    GenTable table = genTableService.selectGenTableById(tableId);
    List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.MSG, table);
    result.put(PublicConstants.PARAM_LIST, list);
    return result;
  }

  /**
   * 修改保存代码生成业务
   */
  @ApiOperation("5.保存生成信息")
  @ApiOperationSupport(order = 5)
  @PutMapping
  public Map<String, Object> editSave(@Validated @RequestBody GenTable genTable) {
    genTableService.validateEdit(genTable);
    genTableService.updateGenTable(genTable);

    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.CODE, ResultEnum.SUCCESS.getCode());
    return result;
  }

  /**
   * 预览代码
   */
  @ApiOperation("6.预览代码")
  @ApiOperationSupport(order = 6)
  @GetMapping("/preview/{tableId}")
  public Map<String, String> preview(@PathVariable("tableId") Long tableId) {
    return genTableService.previewCode(tableId);
  }

  /**
   * 生成代码（下载方式）
   */
  @ApiOperation("7.生成代码下载")
  @ApiOperationSupport(order = 7)
  @GetMapping("/download/{tableName}")
  public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) {
    byte[] data = genTableService.downloadCode(tableName);
    genCode(response, data);
  }

  /**
   * 生成代码（自定义路径）
   */
  @ApiOperation("8.生成代码")
  @ApiOperationSupport(order = 8)
  @GetMapping("/genCode/{tableName}")
  public Map<String, Object> genCode(@PathVariable("tableName") String tableName) {
    genTableService.generatorCode(tableName);
    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.CODE, ResultEnum.SUCCESS.getCode());
    return result;
  }

  /**
   * 同步数据库
   */
  @ApiOperation("9.同步数据库")
  @ApiOperationSupport(order = 9)
  @GetMapping("/syncDb/{tableName}")
  public Map<String, Object> syncDb(@PathVariable("tableName") String tableName) {
    genTableService.syncDb(tableName);
    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.CODE, ResultEnum.SUCCESS.getCode());
    return result;
  }

  /**
   * 批量生成代码
   */
  @ApiOperation("10.批量生成代码")
  @ApiOperationSupport(order = 10)
  @GetMapping("/batchGenCode")
  public void batchGenCode(HttpServletResponse response, String tables) {
    String[] tableNames = ConvertString.toStrArray(tables);
    byte[] data = genTableService.downloadCode(tableNames);
    genCode(response, data);
  }

  /**
   * 删除代码生成
   */
  @ApiOperation("11.删除代码生成")
  @ApiOperationSupport(order = 11)
  @DeleteMapping("/{tableIds}")
  public Map<String, Object> remove(@PathVariable Long[] tableIds) {
    genTableService.deleteGenTableByIds(tableIds);
    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.CODE, ResultEnum.SUCCESS.getCode());
    return result;
  }

  /**
   * 导入表结构（保存）
   */
  @ApiOperation("12.导入表结构")
  @ApiOperationSupport(order = 12)
  @PostMapping("/importTable")
  public Map<String, Object> importTableSave(@ApiIgnore @CurrentUser AuthUser authUser, String tables) {
    String operName = authUser == null ? null : authUser.getUserName();
    String[] tableNames = ConvertString.toStrArray(tables);
    // 查询表信息
    List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
    genTableService.importGenTable(tableList, operName);

    Map<String, Object> result = new HashMap<>(2);
    result.put(PublicConstants.CODE, ResultEnum.SUCCESS.getCode());
    return result;
  }

  /**
   * 生成zip文件
   */
  private void genCode(HttpServletResponse response, byte[] data) {
    try {
      response.reset();
      response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
      response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Generator.zip\"");
      response.setContentLength(data.length);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      IOUtils.write(data, response.getOutputStream());
    } catch (IOException e) {
      LOGGER.error("batchGenCode error: ", e);
    }
  }
}
