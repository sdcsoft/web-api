package cn.com.sdcsoft.webapi.wechat.entity;

import cn.com.sdcsoft.webapi.wechat.interfaces.Order;

import java.io.Serializable;
import java.sql.Timestamp;

public class JinRong_Order implements Serializable,Order {
    private Integer id,Status;
    private String WechatOrderId,OpenId,UnionId,Discount,Mobile,WxNickName,OutTradeNo;
    private Float Total,PaymentAmount;
    private Timestamp PayforDate, CreateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getWechatOrderId() {
        return WechatOrderId;
    }

    public void setWechatOrderId(String wechatOrderId) {
        WechatOrderId = wechatOrderId;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getUnionId() {
        return UnionId;
    }

    public void setUnionId(String unionId) {
        UnionId = unionId;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(Float total) {
        Total = total;
    }

    public Float getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        PaymentAmount = paymentAmount;
    }

    public Timestamp getPayforDate() {
        return PayforDate;
    }

    public void setPayforDate(Timestamp payforDate) {
        PayforDate = payforDate;
    }

    public Timestamp getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Timestamp createDate) {
        CreateDate = createDate;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getWxNickName() {
        return WxNickName;
    }

    public void setWxNickName(String wxNickName) {
        WxNickName = wxNickName;
    }

    public String getOutTradeNo() {
        return OutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        OutTradeNo = outTradeNo;
    }
}
