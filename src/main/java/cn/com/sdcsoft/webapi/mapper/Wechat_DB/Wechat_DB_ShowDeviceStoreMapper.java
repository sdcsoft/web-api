package cn.com.sdcsoft.webapi.mapper.Wechat_DB;
import cn.com.sdcsoft.webapi.wechat.entity.ShowDeviceStore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_ShowDeviceStoreMapper {

    @Select("select ImgStyle,DeviceNo,DeviceName,DeviceType,MqttName from ShowDeviceStore where OpenId=#{openId}")
    List<ShowDeviceStore> getShowDeviceStoreListByopenId(@Param("openId") String openId);

    @Select("select * from ShowDeviceStore where OpenId=#{openId} and DeviceNo=#{deviceNo}")
    ShowDeviceStore getShowDeviceStoreByopenIdAndDeviceNo(@Param("openId") String openId, @Param("deviceNo") String deviceNo);

    @Insert("insert into ShowDeviceStore(ImgStyle,DeviceNo,OpenId,DeviceName,DeviceType,MqttName) values (#{imgStyle},#{deviceNo},#{openId},#{deviceName},#{deviceType},#{mqttName})")
    int insertShowDeviceStore(ShowDeviceStore showDeviceStore);

    @Insert("<script>"+
            "insert into ShowDeviceStore(DeviceNo,ImgStyle,OpenId,DeviceName,DeviceType,MqttName)"
            + "values "
            + "<foreach collection =\"storeList\" item=\"store\" index=\"index\" separator =\",\"> "
            + "(#{store.deviceNo},#{store.imgStyle},#{store.openId},#{store.deviceName},#{store.deviceType},#{store.mqttName})"
            + "</foreach > " +
            "</script>")
    int insertManyShowDeviceStore(@Param("storeList") List<ShowDeviceStore> storeList);


    @Delete("delete from ShowDeviceStore where DeviceNo=#{deviceNo}and OpenId=#{openId}")
    int deleteShowDeviceStore(@Param("deviceNo") String deviceNo, @Param("openId") String openId);

    @Update("update ShowDeviceStore set ImgStyle=#{imgStyle},DeviceName=#{deviceName},DeviceType=#{deviceType} ,MqttName=#{mqttName} where OpenId = #{openId} and DeviceNo = #{deviceNo} ")
    int updateShowDeviceStore(ShowDeviceStore store);
}
