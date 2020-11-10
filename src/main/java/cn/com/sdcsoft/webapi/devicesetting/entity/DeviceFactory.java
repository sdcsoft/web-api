package cn.com.sdcsoft.webapi.devicesetting.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DeviceFactory {
    public static final String FIELD_FACTORY_NAME = "factory";
    public static final String FIELD_DEVICE_TYPE = "deviceType";

    @Id
    private String id;

    private String factory;
    private String deviceType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }
}
