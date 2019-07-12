package cn.com.sdcsoft.webapi.wechat.mapper;

import cn.com.sdcsoft.webapi.wechat.entity.Relation_DeviceControlMap;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Relation_DeviceControlMapMapper {

    @Select("<script>" +
            "select ee.* from Relation_DeviceControlMap ee "+
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
    List<Relation_DeviceControlMap> getRelation_DeviceControlMapListByCondition(Relation_DeviceControlMap relation_deviceControlMap);



    @Insert("insert into Relation_DeviceControlMap (DeviceNo,EmployeeMobile,CreateDatetime) values (#{deviceNo},#{employeeMobile},#{createDatetime})")
    int insertRelation_DeviceControlMap(Relation_DeviceControlMap relation_deviceControlMap);

    @Update("update Relation_DeviceControlMap set DeviceNo=#{deviceNo},EmployeeMobile=#{employeeMobile},CreateDatetime=#{createDatetime} where Id = #{id}")
    int updateRelation_DeviceControlMap(Relation_DeviceControlMap relation_deviceControlMap);

    @Delete("delete from Relation_DeviceControlMap where Id=#{id}")
    int deleteRelation_DeviceControlMap(Integer id);
}
