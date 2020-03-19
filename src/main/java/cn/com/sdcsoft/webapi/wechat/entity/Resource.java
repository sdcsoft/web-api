package cn.com.sdcsoft.webapi.wechat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

public class Resource implements Serializable {
    private Integer id;
    private String ResName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }

}
