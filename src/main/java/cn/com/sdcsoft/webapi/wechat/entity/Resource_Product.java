package cn.com.sdcsoft.webapi.wechat.entity;

import java.io.Serializable;

public class Resource_Product implements Serializable {
    private Integer id,resourceId,rangeType,range;
    private String resourceName;
    private float price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getRangeType() {
        return rangeType;
    }

    public void setRangeType(Integer rangeType) {
        this.rangeType = rangeType;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
