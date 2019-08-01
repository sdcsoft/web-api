package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Store;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_DeviceMapper {

    @Select("select ImgStyle,DeviceNo,DeviceName,DeviceType,MqttName from Store where OpenId=#{openId}")
    List<Store> getWxDeviceListByopenId(@Param("openId") String openId);

    @Insert("insert into Store(ImgStyle,DeviceNo,OpenId,DeviceName,DeviceType,MqttName) values (#{imgStyle},#{deviceNo},#{openId},#{deviceName},#{deviceType},#{mqttName})")
    int insertWxDevice(Store store);

    @Delete("delete from Store where DeviceNo=#{deviceNo}and OpenId=#{openId}")
    int deleteWxDevice(@Param("deviceNo")String deviceNo,@Param("openId")String openId);

    @Update("update Store set ImgStyle=#{imgStyle},DeviceName=#{deviceName},DeviceType=#{deviceType} ,MqttName=#{mqttName} where OpenId = #{openId} and DeviceNo = #{deviceNo} ")
    int updateStore(Store store);
}
