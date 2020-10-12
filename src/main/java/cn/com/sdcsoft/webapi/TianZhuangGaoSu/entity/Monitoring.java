package cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity;

import java.io.Serializable;


public class Monitoring implements Serializable {
    private int id,status;
    private String title, hLSUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String gethLSUrl() {
        return hLSUrl;
    }

    public void sethLSUrl(String hLSUrl) {
        this.hLSUrl = hLSUrl;
    }
}
