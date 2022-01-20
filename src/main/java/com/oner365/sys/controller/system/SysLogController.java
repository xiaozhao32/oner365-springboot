package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.oner365.common.enums.ResultEnum;
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
import com.oner365.sys.dto.SysLogDto;
import com.oner365.sys.service.ISysLogService;
import com.oner365.sys.vo.SysLogVo;
import com.oner365.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统日志控制器
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/log")
@Api(tags = "系统观察 - 日志")
public class SysLogController extends BaseController {

	@Autowired
	private ISysLogService logService;

	/**
	 * 列表
	 *
	 * @param data 查询参数
	 * @return PageInfo<SysLogDto>
	 */
	@ApiOperation("1.获取列表")
	@ApiOperationSupport(order = 1)
	@PostMapping("/list")
	public PageInfo<SysLogDto> list(@RequestBody QueryCriteriaBean data) {
		return logService.pageList(data);
	}

	/**
	 * 获取信息
	 *
	 * @param id 编号
	 * @return SysLogDto
	 */
	@ApiOperation("2.按id查询")
	@ApiOperationSupport(order = 2)
	@GetMapping("/get/{id}")
	public SysLogDto get(@PathVariable String id) {
		return logService.getById(id);
	}

	/**
	 * 保存
	 *
	 * @param sysLogVo 菜单类型对象
	 * @return ResponseResult<String>
	 */
	@ApiOperation("3.保存")
	@ApiOperationSupport(order = 3)
	@PutMapping("/save")
	public ResponseResult<String> save(@RequestBody SysLogVo sysLogVo) {
		if (sysLogVo != null) {
			logService.save(sysLogVo);
			return ResponseResult.success(ResultEnum.SUCCESS.getName());
		}
		return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
	}

	/**
	 * 删除
	 *
	 * @param ids 编号
	 * @return List<Integer>
	 */
	@ApiOperation("4.删除")
	@ApiOperationSupport(order = 4)
	@DeleteMapping("/delete")
	public List<Integer> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> logService.deleteById(id)).collect(Collectors.toList());
  }

	/**
	 * 按日期删除日志
	 *
	 * @param days 天数
	 * @return Integer
	 */
	@ApiOperation("5.删除日志")
	@ApiOperationSupport(order = 5)
	@DeleteMapping("/days/delete")
	public Integer deleteLog(@RequestParam("days") Integer days) {
		Date date = DateUtil.getDateAgo(days);
		return logService.deleteLog(DateUtil.dateToLocalDateTime(date));
	}

	/**
	 * 导出日志
	 *
	 * @param data 查询参数
	 * @return ResponseEntity<byte[]>
	 */
	@ApiOperation("6.导出")
	@ApiOperationSupport(order = 6)
	@PostMapping("/export")
	public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
		List<SysLogDto> list = logService.findList(data);

		String[] titleKeys = new String[] { "编号", "请求IP", "请求方法", "服务名称", "请求地址", "请求内容", "创建时间" };
		String[] columnNames = { "id", "operationIp", "methodName", "operationName", "operationPath",
				"operationContext", "createTime" };

		String fileName = SysLogDto.class.getSimpleName() + System.currentTimeMillis();
		return exportExcel(fileName, titleKeys, columnNames, list);
	}

}
