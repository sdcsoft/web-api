package cn.com.sdcsoft.webapi.wechat.entity;

import java.util.Date;

public class DiscountStrategy {
    /**
     * 满减类型优惠策略
     */
    public static final int Type_Sub=0;
    /**
     * 打折类型优惠策略
     */
    public static final int Type_Discount=1;
    private Integer id;
    private  String  strategyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer baseNumber;
    private Float discount;
    private  Integer strategyType;
    private Date startDatetime,endDatetime;

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Integer getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(Integer baseNumber) {
        this.baseNumber = baseNumber;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public float Count(float money){
        if(Type_Sub == this.strategyType){
            return money - discount;
        }
        return money*discount;
    }
}
