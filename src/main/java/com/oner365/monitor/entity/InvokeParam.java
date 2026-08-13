package com.oner365.monitor.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * 定时任务调度
 *
 * @author liutao
 */
public class InvokeParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 执行任务服务名称
     */
    private String taskServerName;

    /**
     * 执行计划任务是否可并发 0：不允许 1:允许
     */
    private String concurrent;

    /**
     * 任务所需参数
     */
    private Map<String, Object> taskParam;

    public InvokeParam() {
        super();
    }
    
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskServerName() {
        return taskServerName;
    }

    public void setTaskServerName(String taskServerName) {
        this.taskServerName = taskServerName;
    }

    public String getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(String concurrent) {
        this.concurrent = concurrent;
    }

    public Map<String, Object> getTaskParam() {
        return taskParam;
    }

    public void setTaskParam(Map<String, Object> taskParam) {
        this.taskParam = taskParam;
    }

}
