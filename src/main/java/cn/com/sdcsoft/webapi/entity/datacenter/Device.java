package cn.com.sdcsoft.webapi.entity.datacenter;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备数据结构
 */
public class Device implements Serializable {
    public static final int STATUS_SELL = 1;
    public static final int STATUS_READY = 0;

    private int id;
    private int enterpriseId;
    private int customerId;
    private int agentId;
    private int endUserId;

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(int endUserId) {
        this.endUserId = endUserId;
    }

    private int status;
    private int runStatus;
    private int power;

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    private int media;
    private String deviceNo;
    /**
     * 设备前10位编码
     */
    private String devicePrefix;
    /**
     * 设备后10位编码
     */
    private String deviceSuffix;
    /**
     * 设备类型 如：CTL_NJZJ_IPK2 PLC_BKSE_DRY
     */
    private String deviceType;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    private String subType;

    public String getDevicePrefix() {
        return devicePrefix;
    }

    public void setDevicePrefix(String devicePrefix) {
        this.devicePrefix = devicePrefix;
    }

    public String getDeviceSuffix() {
        return deviceSuffix;
    }

    public void setDeviceSuffix(String deviceSuffix) {
        this.deviceSuffix = deviceSuffix;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date importDatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceno) {
        this.deviceNo = deviceno;
    }


    public Date getImportDatetime() {
        return importDatetime;
    }

    public void setImportDatetime(Date importDatetime) {
        this.importDatetime = importDatetime;
    }

}
