package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Relation_DeviceSmsMap;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_Relation_DeviceSmsMapMapper {

    @Select("select * from Relation_DeviceSmsMap ")
    List<Relation_DeviceSmsMap> getRelation_DeviceSmsMap();


    @Select("select * from Relation_DeviceSmsMap where DeviceNo=#{deviceNo} and EmployeeMobile=#{employeeMobile}")
    List<Relation_DeviceSmsMap> getRelation_DeviceSmsMapByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo, @Param("employeeMobile") String employeeMobile);

    @Select("select * from Relation_DeviceSmsMap where DeviceNo=#{deviceNo} and EmployeeMobile=#{employeeMobile}")
    Relation_DeviceSmsMap getRelation_DeviceSmsMapOneByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo, @Param("employeeMobile") String employeeMobile);


    @Insert("<script>" +
            "insert into Relation_DeviceSmsMap(DeviceNo,EmployeeMobile,CreateDatetime,DueTime)"
            + "values "
            + "<foreach collection =\"rrList\" item=\"rr\" index=\"index\" separator =\",\"> "
            + "(#{rr.deviceNo},#{rr.employeeMobile},#{rr.createDatetime},#{rr.dueTime})"
            + "</foreach > " +
            "</script>")
    int insertManyRelation_DeviceSmsMap(@Param("rrList") List<Relation_DeviceSmsMap> rrList);

    @Update("update Relation_DeviceSmsMap set DeviceNo=#{deviceNo},EmployeeMobile=#{employeeMobile},CreateDatetime=#{createDatetime} ,DueTime=#{dueTime}where DeviceNo = #{deviceNo} and EmployeeMobile = #{employeeMobile} ")
    int updateRelation_DeviceSmsMap(Relation_DeviceSmsMap relation_deviceSmsMap);

    @Delete("delete from Relation_DeviceSmsMap where Id=#{id}")
    int deleteRelation_DeviceSmsMap(Integer id);
}
