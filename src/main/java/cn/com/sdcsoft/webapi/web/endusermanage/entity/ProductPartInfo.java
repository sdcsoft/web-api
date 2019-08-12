package cn.com.sdcsoft.webapi.web.endusermanage.entity;

import java.io.Serializable;

public class ProductPartInfo implements Serializable {

    private Integer id,productId, partCategoryId, partSubCategoryId,amountOfUser;
    private String brandName,modelName,supplier,remarks;
    private String partCategoryName ,partSubCategoryName;

    public String getPartCategoryName() {
        return partCategoryName;
    }

    public void setPartCategoryName(String partCategoryName) {
        this.partCategoryName = partCategoryName;
    }

    public String getPartSubCategoryName() {
        return partSubCategoryName;
    }

    public void setPartSubCategoryName(String partSubCategoryName) {
        this.partSubCategoryName = partSubCategoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPartCategoryId() {
        return partCategoryId;
    }

    public void setPartCategoryId(Integer partCategoryId) {
        this.partCategoryId = partCategoryId;
    }

    public Integer getPartSubCategoryId() {
        return partSubCategoryId;
    }

    public void setPartSubCategoryId(Integer partSubCategoryId) {
        this.partSubCategoryId = partSubCategoryId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getAmountOfUser() {
        return amountOfUser;
    }

    public void setAmountOfUser(Integer amountOfUser) {
        this.amountOfUser = amountOfUser;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
