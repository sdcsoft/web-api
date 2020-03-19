package cn.com.sdcsoft.webapi.wechat.entity;

import java.io.Serializable;

public class JinRong_OrderItem implements Serializable {
    private Integer id,OrderId,ResourceId,Range,Amount,RangeType;
    private String ResourceName,DeviceNo,Mobile,Marker;
    private Float Price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
        OrderId = orderId;
    }

    public Integer getResourceId() {
        return ResourceId;
    }

    public void setResourceId(Integer resourceId) {
        ResourceId = resourceId;
    }

    public Integer getRange() {
        return Range;
    }

    public void setRange(Integer range) {
        Range = range;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Integer getRangeType() {
        return RangeType;
    }

    public void setRangeType(Integer rangeType) {
        RangeType = rangeType;
    }

    public String getResourceName() {
        return ResourceName;
    }

    public void setResourceName(String resourceName) {
        ResourceName = resourceName;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        DeviceNo = deviceNo;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMarker() {
        return Marker;
    }

    public void setMarker(String marker) {
        Marker = marker;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }
}
