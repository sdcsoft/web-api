package cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity;

import java.io.Serializable;


public class Device implements Serializable {
    private int id,Type;
    private String deviceNo,deviceName,pointIndexMap,deviceDataMap;


    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPointIndexMap() {
        return pointIndexMap;
    }

    public void setPointIndexMap(String pointIndexMap) {
        this.pointIndexMap = pointIndexMap;
    }

    public String getDeviceDataMap() {
        return deviceDataMap;
    }

    public void setDeviceDataMap(String deviceDataMap) {
        this.deviceDataMap = deviceDataMap;
    }
}
