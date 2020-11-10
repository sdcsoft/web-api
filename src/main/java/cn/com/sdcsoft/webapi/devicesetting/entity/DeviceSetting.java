package cn.com.sdcsoft.webapi.devicesetting.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class DeviceSetting {

    public static final String FIELD_ID="id";
    public static final String FIELD_DEVICE_NO ="deviceNo";

    @Id
    private String id;
    private String deviceMapId,deviceMapTitle;
    private List<String> params;
    private String deviceNo,stationNo;
    private Date settingDate;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceMapId() {
        return deviceMapId;
    }

    public void setDeviceMapId(String deviceMapId) {
        this.deviceMapId = deviceMapId;
    }

    public String getDeviceMapTitle() {
        return deviceMapTitle;
    }

    public void setDeviceMapTitle(String deviceMapTitle) {
        this.deviceMapTitle = deviceMapTitle;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public Date getSettingDate() {
        return settingDate;
    }

    public void setSettingDate(Date settingDate) {
        this.settingDate = settingDate;
    }
}
