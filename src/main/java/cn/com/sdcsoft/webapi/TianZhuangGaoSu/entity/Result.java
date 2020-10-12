package cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity;

import java.io.Serializable;


public class Result implements Serializable {
    private int count;
    private String date;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
