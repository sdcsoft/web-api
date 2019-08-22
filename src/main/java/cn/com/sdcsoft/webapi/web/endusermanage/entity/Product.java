package cn.com.sdcsoft.webapi.web.endusermanage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    public static final int ROLE_ADMIN = 3;
    public static final int ROLE_COMMONUSER = 5;

    private Integer id;
    private Integer orgId;               //组织Id
    private String boilerNo;
    private String nickName;//锅炉编号
    private String controllerNo;        //控制器编号
    private Float tonnageNum;            //吨位
    private Integer media;             //介质
    private Integer power;               //燃料
    private Timestamp createDateTime;   //创建时间
    private List<ProductUser> deleteProductUserList;
    private List<ProductUser> selectProductUserList;
    List<ProductPartInfo> ProductAuxiliaryMachineInfoList = new ArrayList<>();


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public List<ProductUser> getDeleteProductUserList() {
        return deleteProductUserList;
    }

    public void setDeleteProductUserList(List<ProductUser> deleteProductUserList) {
        this.deleteProductUserList = deleteProductUserList;
    }

    public List<ProductUser> getSelectProductUserList() {
        return selectProductUserList;
    }

    public void setSelectProductUserList(List<ProductUser> selectProductUserList) {
        this.selectProductUserList = selectProductUserList;
    }

    public List<ProductPartInfo> getProductAuxiliaryMachineInfoList() {
        return ProductAuxiliaryMachineInfoList;
    }

    public void setProductAuxiliaryMachineInfoList(List<ProductPartInfo> productAuxiliaryMachineInfoList) {
        ProductAuxiliaryMachineInfoList = productAuxiliaryMachineInfoList;
    }

    public String getControllerNo() {
        return controllerNo;
    }

    public void setControllerNo(String controllerNo) {
        this.controllerNo = controllerNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoilerNo() {
        return boilerNo;
    }

    public void setBoilerNo(String boilerNo) {
        this.boilerNo = boilerNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Float getTonnageNum() {
        return tonnageNum;
    }

    public void setTonnageNum(Float tonnageNum) {
        this.tonnageNum = tonnageNum;
    }

    public Integer getMedia() {
        return media;
    }

    public void setMedia(Integer media) {
        this.media = media;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
