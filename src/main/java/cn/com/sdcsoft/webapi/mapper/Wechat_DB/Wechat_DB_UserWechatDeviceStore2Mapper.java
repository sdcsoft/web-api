package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.DeviceStore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_UserWechatDeviceStore2Mapper {

    @Select("select DeviceNo from UserWechatDeviceStore2")
    List<String> getWxDeviceMapping();

    @Select("select ImgStyle,DeviceNo,DeviceName,DeviceType,MqttName from UserWechatDeviceStore2 where OpenId=#{openId}")
    List<DeviceStore> getWxDeviceListByopenId(@Param("openId") String openId);

    @Select("select * from UserWechatDeviceStore2 where OpenId=#{openId} and DeviceNo=#{deviceNo}")
    DeviceStore getWxDeviceByopenIdAndDeviceNo(@Param("openId") String openId, @Param("deviceNo") String deviceNo);

    @Insert("insert into UserWechatDeviceStore2(ImgStyle,DeviceNo,OpenId,DeviceName,DeviceType,MqttName) values (#{imgStyle},#{deviceNo},#{openId},#{deviceName},#{deviceType},#{mqttName})")
    int insertWxDevice(DeviceStore store);

    @Insert("<script>" +
            "insert into UserWechatDeviceStore2(DeviceNo,ImgStyle,OpenId,DeviceName,DeviceType,MqttName)"
            + "values "
            + "<foreach collection =\"storeList\" item=\"store\" index=\"index\" separator =\",\"> "
            + "(#{store.deviceNo},#{store.imgStyle},#{store.openId},#{store.deviceName},#{store.deviceType},#{store.mqttName})"
            + "</foreach > " +
            "</script>")
    int insertManyStore(@Param("storeList") List<DeviceStore> storeList);


    @Delete("delete from UserWechatDeviceStore2 where DeviceNo=#{deviceNo}and OpenId=#{openId}")
    int deleteWxDevice(@Param("deviceNo") String deviceNo, @Param("openId") String openId);

    @Update("update UserWechatDeviceStore2 set ImgStyle=#{imgStyle},DeviceName=#{deviceName},DeviceType=#{deviceType} ,MqttName=#{mqttName} where OpenId = #{openId} and DeviceNo = #{deviceNo} ")
    int updateStore(DeviceStore store);
}
