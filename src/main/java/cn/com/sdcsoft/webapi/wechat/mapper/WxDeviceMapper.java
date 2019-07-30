package cn.com.sdcsoft.webapi.wechat.mapper;

import cn.com.sdcsoft.webapi.wechat.entity.Relation_DeviceControlMap;
import cn.com.sdcsoft.webapi.wechat.entity.WxDevice;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WxDeviceMapper {

    @Select("select * from WxDevice where EmployeeMobile=#{employeeMobile}")
    List<WxDevice> getWxDeviceListByemployeeMobile(@Param("employeeMobile") String employeeMobile);

    @Insert("insert into WxDevice(Imgstyle,Type,DeviceNo,EmployeeMobile,DeviceName,DeviceType,MqttName) values (#{imgstyle},#{type},#{deviceNo},#{employeeMobile},#{deviceName},#{deviceType},#{mqttName})")
    int insertWxDevice(WxDevice wxDevice);

    @Delete("delete from WxDevice where DeviceNo=#{deviceNo}and EmployeeMobile=#{employeeMobile}")
    int deleteWxDevice(@Param("deviceNo")String deviceNo,@Param("employeeMobile")String employeeMobile);
}
