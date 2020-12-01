package cn.com.sdcsoft.webapi.devicesetting.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class DeviceMap {
    public static final String FIELD_ID="id";
    public static final String FIELD_DEVICE_TYPE="deviceType";
    public static final String FIELD_DEVICE_FACTORY="deviceFactory";
    public static final String FIELD_DEVICE_LINE="deviceLine";
    public static final String FIELD_DEVICE_ATTR="deviceAttr";

    @Id
    private String id;

    private String deviceType,deviceFactory,deviceLine,deviceAttr,title,pointIndexMap,deviceDataMap;
    private int deviceDataLength,status,share;
    private Date createDatetime;
    private List<String> otherCommands,params;
    private List<ModbusSetting> modbusSettings;
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    public String getDeviceLine() {
        return deviceLine;
    }

    public void setDeviceLine(String deviceLine) {
        this.deviceLine = deviceLine;
    }

    public String getDeviceAttr() {
        return deviceAttr;
    }

    public void setDeviceAttr(String deviceAttr) {
        this.deviceAttr = deviceAttr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getDeviceDataLength() {
        return deviceDataLength;
    }

    public void setDeviceDataLength(int deviceDataLength) {
        this.deviceDataLength = deviceDataLength;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public List<String> getOtherCommands() {
        return otherCommands;
    }

    public void setOtherCommands(List<String> otherCommands) {
        this.otherCommands = otherCommands;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public List<ModbusSetting> getModbusSettings() {
        return modbusSettings;
    }

    public void setModbusSettings(List<ModbusSetting> modbusSettings) {
        this.modbusSettings = modbusSettings;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
