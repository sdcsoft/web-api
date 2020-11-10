package cn.com.sdcsoft.webapi.dtusetting.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DeviceAttr {
    public  static final String FIELD_DEVICE_LINE ="deviceLine";

    @Id
    private String id;

    private String deviceLine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceLine() {
        return deviceLine;
    }

    public void setDeviceLine(String deviceLine) {
        this.deviceLine = deviceLine;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    private String attr;
}
