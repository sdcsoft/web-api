package cn.com.sdcsoft.webapi.web.boilermanage.entity;

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
    private String boilerNo;            //锅炉编号
    private Integer productCategoryId;  //锅炉型号
    private String productCategoryName;//锅炉型号名称
    private String controllerNo;        //控制器编号
    private Float tonnageNum;            //吨位
    private Integer media;             //介质
    private Integer power;               //燃料
    private Integer isSell;             //是否售出
    private Date saleDate;              //售出日期
    private String longitude;           //经度
    private String latitude;            //纬度
    private String province;            //省
    private String city;                //市
    private String district;            //区
    private String street;              //街道
    private Timestamp createDateTime;   //创建时间
    private Timestamp editDateTime;     //编辑时间
    private Integer customerId;    //客户Id
    private String customerName;   //客户姓名
    private List<ProductUser> deleteProductUserList;
    private List<ProductUser> selectProductUserList;
    List<ProductPartInfo> ProductAuxiliaryMachineInfoList = new ArrayList<>();

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

    public Integer getIsSell() {
        return isSell;
    }

    public void setIsSell(Integer isSell) {
        this.isSell = isSell;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getEditDateTime() {
        return editDateTime;
    }

    public void setEditDateTime(Timestamp editDateTime) {
        this.editDateTime = editDateTime;
    }


    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }
}
