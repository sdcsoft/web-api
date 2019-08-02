package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.web.weixinmanage.entity.DeviceUserControlMap;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_DeviceUserControlMapMapper {

    @Select("<script>" +
            "select ee.* from DeviceUserControlMap ee "+
            "<where>"+
            " 1=1 "+
            "<if test='deviceNo != null '> "+
            " AND ee.DeviceNo LIKE CONCAT(CONCAT('%',#{deviceNo}),'%')"+
            "</if>"+
            "<if test='employeeMobile != null'> "+
            " AND ee.EmployeeMobile LIKE CONCAT(CONCAT('%',#{employeeMobile}),'%')"+
            "</if>"+
            "</where>"+
            "</script>")
    List<DeviceUserControlMap> getDeviceUserControlMapListByCondition(DeviceUserControlMap deviceUserControlMap);

    @Select("select *from DeviceUserControlMap where OpenId=#{openId}")
    List<DeviceUserControlMap> getDeviceUserControlMapListByopenId(@Param("openId") String openId);


    @Insert("insert into DeviceUserControlMap (DeviceNo,EmployeeMobile,CreateDatetime,OpenId) values (#{deviceNo},#{employeeMobile},#{createDatetime},#{openId})")
    int insertDeviceUserControlMap(DeviceUserControlMap deviceUserControlMap);

    @Update("update DeviceUserControlMap set DeviceNo=#{deviceNo},EmployeeMobile=#{employeeMobile},CreateDatetime=#{createDatetime},OpenId=#{openId} where Id = #{id}")
    int updateDeviceUserControlMap(DeviceUserControlMap deviceUserControlMap);

    @Delete("delete from DeviceUserControlMap where Id=#{id}")
    int deleteDeviceUserControlMap(Integer id);
}
