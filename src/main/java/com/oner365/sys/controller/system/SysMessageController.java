package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.AttributeBean;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.log.annotation.SysLog;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.enums.MessageStatusEnum;
import com.oner365.sys.enums.MessageTypeEnum;
import com.oner365.sys.service.ISysMessageService;
import com.oner365.sys.vo.SysMessageVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 消息通知
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 消息")
@RequestMapping("/system/message")
public class SysMessageController extends BaseController {

    @Resource
    private ISysMessageService sysMessageService;

    /**
     * 查询结果 有返回 true 并且已读
     * @param messageType 消息类型
     * @return Boolean
     */
    @ApiOperation("1.刷新")
    @ApiOperationSupport(order = 1)
    @GetMapping("/refresh")
    public Boolean refresh(@RequestParam MessageTypeEnum messageType) {
        QueryCriteriaBean data = new QueryCriteriaBean();
        data.setWhereList(Collections.singletonList(new AttributeBean("messageType", messageType)));
        List<SysMessageDto> list = sysMessageService.findList(data);
        if (!list.isEmpty()) {
            list.forEach(entity -> sysMessageService.editStatus(entity.getId(), MessageStatusEnum.READ));
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 获取列表
     * @param data 查询对象
     * @return PageInfo<SysMessageDto>
     */
    @ApiOperation("2.获取列表")
    @ApiOperationSupport(order = 2)
    @PostMapping("/list")
    public PageInfo<SysMessageDto> pageList(@RequestBody QueryCriteriaBean data) {
        return sysMessageService.pageList(data);
    }

    /**
     * 按id查询
     * @param id 编号
     * @return SysMessageDto
     */
    @ApiOperation("3.按id查询")
    @ApiOperationSupport(order = 3)
    @GetMapping("/get/{id}")
    public SysMessageDto get(@PathVariable String id) {
        return sysMessageService.getById(id);
    }

    /**
     * 修改状态
     * @param id 主键
     * @param status 状态
     * @return Boolean
     */
    @ApiOperation("4.修改状态")
    @ApiOperationSupport(order = 4)
    @SysLog("修改消息状态")
    @PostMapping("/status/{id}")
    public Boolean editStatus(@PathVariable String id, @RequestParam MessageStatusEnum status) {
        return sysMessageService.editStatus(id, status);
    }

    /**
     * 消息保存
     * @param sysMessageVo 消息对象
     * @return SysMessageDto
     */
    @ApiOperation("5.保存")
    @ApiOperationSupport(order = 5)
    @SysLog("保存消息")
    @PutMapping("/save")
    public SysMessageDto save(@Validated @RequestBody SysMessageVo sysMessageVo) {
        return sysMessageService.save(sysMessageVo);
    }

    /**
     * 删除消息
     * @param ids 编号
     * @return List<Boolean>
     */
    @ApiOperation("6.删除")
    @ApiOperationSupport(order = 6)
    @SysLog("删除消息")
    @DeleteMapping("/delete")
    public List<Boolean> delete(@RequestBody String... ids) {
        return Arrays.stream(ids).map(id -> sysMessageService.deleteById(id)).collect(Collectors.toList());
    }

    /**
     * 导出Excel
     * @param data 查询参数
     * @return ResponseEntity<byte[]>
     */
    @ApiOperation("7.导出")
    @ApiOperationSupport(order = 7)
    @PostMapping("/export")
    public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
        List<SysMessageDto> list = sysMessageService.findList(data);

        String[] titleKeys = new String[] { "编号", "队列类型", "队列标识", "消息类型", "消息名称", "消息内容", "发送者", "接受者", "状态", "创建时间",
                "更新时间" };
        String[] columnNames = { "id", "queueType", "queueKey", "messageType", "messageName", "context", "sendUser",
                "receiveUser", "status", "createTime", "updateTime" };

        String fileName = SysMessageDto.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
