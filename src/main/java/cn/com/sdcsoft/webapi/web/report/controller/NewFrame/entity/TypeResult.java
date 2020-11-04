package cn.com.sdcsoft.webapi.web.report.controller.NewFrame.entity;

import java.io.Serializable;

public class TypeResult implements Serializable {
    private String value, name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
