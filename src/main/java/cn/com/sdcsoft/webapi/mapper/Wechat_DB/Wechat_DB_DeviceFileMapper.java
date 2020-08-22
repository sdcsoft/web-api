package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.DeviceFile;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_DeviceFileMapper {

    @Select("select * from DeviceFile where DeviceNo=#{deviceNo}")
    List<DeviceFile> getDeviceFileByDeviceNo(@Param("deviceNo") String deviceNo);

    @Select("<script>" +
            "select ee.* from DeviceFile ee " +
            "<where>" +
            " 1=1 " +
            "<if test='deviceNo != null and deviceNo.length>0 '> " +
            " AND ee.DeviceNo LIKE CONCAT(CONCAT('%',#{deviceNo}),'%')" +
            "</if>" +
            "<if test='employeeMobile != null and employeeMobile.length>0 '> " +
            " AND ee.EmployeeMobile LIKE CONCAT(CONCAT('%',#{employeeMobile}),'%')" +
            "</if>" +
            "</where>" +
            "</script>")
    List<DeviceFile> getDeviceFileListByCondition(DeviceFile deviceFile);

    @Insert("insert into DeviceFile(DeviceNo,FileName,CreateDatetime,EmployeeMobile) values (#{deviceNo},#{fileName},#{createDatetime},#{employeeMobile})")
    int insertDeviceFile(DeviceFile deviceFile);

    @Delete("delete from DeviceFile where Id=#{id}")
    int deleteDeviceFile(@Param("id") String id);

    @Update("update DeviceFile set DeviceNo=#{deviceNo},FilePath=#{filePath},CreateDatetime=#{createDatetime},Author=#{author} where Id = #{id}")
    int updateDeviceFileById(DeviceFile deviceFile);

}
