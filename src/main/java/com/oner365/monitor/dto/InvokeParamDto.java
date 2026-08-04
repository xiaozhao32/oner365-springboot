package com.oner365.monitor.dto;

import java.io.Serializable;

import com.oner365.data.commons.util.GsonUtils;

import jakarta.json.JsonObject;

/**
 * 定时任务调度表
 *
 * @author liutao
 */
public class InvokeParamDto implements Serializable {

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 任务所需参数
     */
    private JsonObject taskParam;

    public InvokeParamDto() {
        super();
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

    public JsonObject getTaskParam() {
        return taskParam;
    }

    public void setTaskParam(JsonObject taskParam) {
        this.taskParam = taskParam;
    }

    @Override
    public String toString() {
        return GsonUtils.objectToJson(this);
    }

}
