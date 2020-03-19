package cn.com.sdcsoft.webapi.wechat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

public class Role_Resource implements Serializable {
    private Integer id, resId,range,rangeType,amount;
    private String openId,Mobile,deviceNo;
    private Timestamp dueTime;

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Integer getRangeType() {
        return rangeType;
    }

    public void setRangeType(Integer rangeType) {
        this.rangeType = rangeType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getDueTime() {
        return dueTime;
    }

    public void setDueTime(Timestamp dueTime) {
        this.dueTime = dueTime;
    }
}
