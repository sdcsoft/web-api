package cn.com.sdcsoft.webapi.wechat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserAddDeviceHistory implements Serializable {


    private Integer id;
    private String openId, deviceNo;
    private Timestamp bindTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getBindTime() {
        return bindTime;
    }

    public void setBindTime(Timestamp bindTime) {
        this.bindTime = bindTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
