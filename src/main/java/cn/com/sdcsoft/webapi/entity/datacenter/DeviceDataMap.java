package cn.com.sdcsoft.webapi.entity.datacenter;

import java.util.Date;

public class DeviceDataMap {
    private Integer id;
    private String title;
    private String author;
    private Date createDatetime;
    private Integer deviceDataLength;
    private String pointIndexMap, deviceDataMap;
    private Integer status;
    private boolean share;

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Integer getDeviceDataLength() {
        return deviceDataLength;
    }

    public void setDeviceDataLength(Integer deviceDataLength) {
        this.deviceDataLength = deviceDataLength;
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
}
