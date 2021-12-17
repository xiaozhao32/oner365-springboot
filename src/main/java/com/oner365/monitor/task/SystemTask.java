package com.oner365.monitor.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.rabbitmq.IScheduleSendTaskService;
import com.oner365.sys.service.ISysLogService;
import com.oner365.util.DateUtil;

/**
 * 定时任务调度测试
 * 
 * @author liutao
 */
@Component("systemTask")
public class SystemTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTask.class);
    
    @Autowired
    private IScheduleSendTaskService scheduleTaskService;
    
    @Autowired
    private ISysLogService sysLogService;

    public void taskMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        LOGGER.info("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i);
    }

    public void taskParams(String params) {
        LOGGER.info("执行有参方法：{}", params);
    }

    public void taskNoParams() {
        LOGGER.info("执行无参方法");
    }
    
    public void taskDeleteLog(Integer days) {
    	Date date = DateUtil.getDateAgo(days);
    	int isSuccess = sysLogService.deleteLog(DateUtil.dateToLocalDateTime(date));
    	LOGGER.info("delete log days:{}, isSuccess:{}", days, isSuccess);
    }
    
    public void taskRun(InvokeParamDto param) {
        LOGGER.info("param: {}",param);
        scheduleTaskService.pullTask(JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(param)),InvokeParamDto.class));
    }
}
