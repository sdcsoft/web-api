package cn.com.sdcsoft.webapi.wechat.entity;

import java.io.Serializable;

public class DeviceStore implements Serializable {
    private Integer id, imgStyle;
    private String deviceNo, openId, deviceName, deviceType, mqttName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImgStyle() {
        return imgStyle;
    }

    public void setImgStyle(Integer imgStyle) {
        this.imgStyle = imgStyle;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getMqttName() {
        return mqttName;
    }

    public void setMqttName(String mqttName) {
        this.mqttName = mqttName;
    }


}
