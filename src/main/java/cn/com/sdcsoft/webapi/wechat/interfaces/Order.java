package cn.com.sdcsoft.webapi.wechat.interfaces;

public interface Order {
    float getTotal();
    void setDiscount(String discount);
    void setPaymentAmount(float money);
}
