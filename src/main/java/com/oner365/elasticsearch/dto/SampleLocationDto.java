package com.oner365.elasticsearch.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 坐标信息
 *
 * @author zhaoyong
 *
 */
@Schema(description = "坐标信息")
public class SampleLocationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 坐标名称
     */
    @Schema(description = "坐标名称")
    private String locationName;

    /**
     * 坐标信息
     */
    @Schema(description = "坐标信息")
    private GeoPoint locationPoint;

    /**
     * 坐标描述
     */
    @Schema(description = "坐标描述")
    private String locationDesc;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 构造方法
     */
    public SampleLocationDto() {
        super();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * @param locationName the locationName to set
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * @return the locationPoint
     */
    public GeoPoint getLocationPoint() {
        return locationPoint;
    }

    /**
     * @param locationPoint the locationPoint to set
     */
    public void setLocationPoint(GeoPoint locationPoint) {
        this.locationPoint = locationPoint;
    }

    /**
     * @return the locationDesc
     */
    public String getLocationDesc() {
        return locationDesc;
    }

    /**
     * @param locationDesc the locationDesc to set
     */
    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    /**
     * @return the createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}
