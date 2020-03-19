package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.JinRong_OrderItem;
import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface Wechat_DB_JinRong_OrderItemMapper {

    @Select("select * from JinRong_OrderItem where OrderId=#{OrderId}")
    List<JinRong_OrderItem> getJinRong_OrderItemListByOrderId(@Param("OrderId") String OrderId);


    @Insert("<script>" +
            "insert into JinRong_OrderItem(OrderId,ResourceId,ResourceName,JinRong_OrderItem.Range,Price,Amount,DeviceNo,Mobile,Marker,RangeType)"
            + "values "
            + "<foreach collection =\"itemList\" item=\"order\" index=\"index\" separator =\",\"> "
            + "(#{order.orderId},#{order.resourceId},#{order.resourceName},#{order.range},#{order.price},#{order.amount},#{order.deviceNo},#{order.mobile},#{order.marker},#{order.rangeType})"
            + "</foreach > " +
            "</script>")
    int insertJinRong_OrderItem(@Param("itemList") List<JinRong_OrderItem> itemList);
}
