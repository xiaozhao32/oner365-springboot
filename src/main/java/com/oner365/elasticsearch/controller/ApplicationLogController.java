package com.oner365.elasticsearch.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.elasticsearch.dto.ApplicationLogDto;
import com.oner365.elasticsearch.service.IApplicationLogElasticsearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Elasticsearch Controller
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "ApplicationLog 应用日志")
@RequestMapping("/elasticsearch/application/log")
public class ApplicationLogController extends BaseController {

  @Autowired
  private IApplicationLogElasticsearchService service;

  /**
   * 列表
   *
   * @param data 查询条件参数
   * @return PageInfo<ApplicationLogDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<ApplicationLogDto> list(@RequestBody QueryCriteriaBean data) {
    return this.service.findList(data);
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return ApplicationLogDto
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ApplicationLogDto get(@PathVariable("id") String id) {
    return service.findById(id);
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return Integer
   */
  @ApiOperation("3.删除")
  @ApiOperationSupport(order = 4)
  @DeleteMapping("/delete")
  public Integer delete(@RequestBody String... ids) {
    Integer result = ResultEnum.SUCCESS.getCode();
    Arrays.stream(ids).forEach(id -> service.deleteById(id));
    return result;
  }

}
