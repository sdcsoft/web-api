package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.JinRong_Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface Wechat_DB_JinRong_OrderMapper {

    @Select("select * from JinRong_Order")
    List<JinRong_Order> getJinRong_OrderList();

    @Select("select * from JinRong_Order where OpenId=#{openId}")
    List<JinRong_Order> getJinRong_OrderListByOpenid(@Param("openId") String openId);

    @Select("select * from JinRong_Order where OutTradeNo=#{outTradeNo}")
    JinRong_Order getJinRong_OrderListByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    @Insert("insert into JinRong_Order(Status,WechatOrderId,OpenId,Discount,Total,PaymentAmount,PayforDate,CreateDate,Mobile,WxNickName,OutTradeNo) values (#{Status},#{WechatOrderId},#{OpenId},#{Discount},#{Total},#{PaymentAmount},#{PayforDate},#{CreateDate},#{Mobile},#{WxNickName},#{OutTradeNo})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertJinRong_Order(JinRong_Order jinRong_order);

    @Update("update JinRong_Order set Status=#{Status},WechatOrderId=#{WechatOrderId} where Id = #{id}")
    int updateJinRong_Order(JinRong_Order jinRong_order);
}
