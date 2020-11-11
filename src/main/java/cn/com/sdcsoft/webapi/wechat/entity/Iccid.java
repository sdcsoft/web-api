package cn.com.sdcsoft.webapi.wechat.entity;

import java.io.Serializable;

public class Iccid implements Serializable {
    private String iccid,version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
}
