package com.oner365.monitor.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 定时任务调度
 * 
 * @author liutao
 */
public class InvokeParam implements Serializable {
    private static final long serialVersionUID = 1L;


    /** 
     *    任务编号  
     */
    private String taskId;
    
    /** 
     *    执行任务服务名称  
     */
    private String taskServerName;
    
    /** 
     *    执行计划任务是否可并发
     *    0：不允许
     *    1:允许
     */
    private String concurrent;
    
    
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /** 
     *    任务所需参数
     */
    private JSONObject taskParam;
    
    public InvokeParam() {
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

    public JSONObject getTaskParam() {
        return taskParam;
    }

    public void setTaskParam(JSONObject taskParam) {
        this.taskParam = taskParam;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
