package cn.com.sdcsoft.webapi.wechat.entity;

import java.io.Serializable;

public class TypeResult implements Serializable {
    private String openId, count,sum;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
