package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Store;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_DeviceMapper {

    @Select("select * from Store where EmployeeMobile=#{employeeMobile}")
    List<Store> getWxDeviceListByemployeeMobile(@Param("employeeMobile") String employeeMobile);

    @Insert("insert into Store(Imgstyle,Type,DeviceNo,EmployeeMobile,DeviceName,DeviceType,MqttName) values (#{imgstyle},#{type},#{deviceNo},#{employeeMobile},#{deviceName},#{deviceType},#{mqttName})")
    int insertWxDevice(Store store);

    @Delete("delete from Store where DeviceNo=#{deviceNo}and EmployeeMobile=#{employeeMobile}")
    int deleteWxDevice(@Param("deviceNo")String deviceNo,@Param("employeeMobile")String employeeMobile);
}
