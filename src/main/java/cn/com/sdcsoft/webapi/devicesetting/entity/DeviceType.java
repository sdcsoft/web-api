package cn.com.sdcsoft.webapi.devicesetting.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DeviceType {
    public  static final String FIELD_FACTORY_NAME="factory";
    @Id
    private String id;

    private String typeName;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
