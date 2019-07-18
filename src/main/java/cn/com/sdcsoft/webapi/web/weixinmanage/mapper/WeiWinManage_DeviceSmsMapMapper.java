package cn.com.sdcsoft.webapi.web.weixinmanage.mapper;
import cn.com.sdcsoft.webapi.web.weixinmanage.entity.Relation_DeviceSmsMap;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WeiWinManage_DeviceSmsMapMapper {
    @Select("<script>" +
            "select ee.* from Relation_DeviceSmsMap ee "+
            "<where>"+
            " 1=1 "+
            "<if test='deviceNo != null and deviceNo.length>0 '> "+
            " AND ee.DeviceNo LIKE CONCAT(CONCAT('%',#{deviceNo}),'%')"+
            "</if>"+
            "<if test='employeeMobile != null and employeeMobile.length>0 '> "+
            " AND ee.EmployeeMobile LIKE CONCAT(CONCAT('%',#{employeeMobile}),'%')"+
            "</if>"+
            "</where>"+
            "</script>")
    List<Relation_DeviceSmsMap> getRelation_DeviceSmsMapListByCondition(Relation_DeviceSmsMap relation_DeviceSmsMap);

    @Select("<script>" +
            "select ee.* from Relation_DeviceSmsMap ee "+
            "<where>"+
            " 1=1 "+
            "<if test='employeeMobile != null and employeeMobile.length>0 '> "+
            " AND ee.EmployeeMobile = #{employeeMobile}"+
            "</if>"+
            "</where>"+
            "</script>")
    List<Relation_DeviceSmsMap> getRelation_DeviceSmsMapList(Relation_DeviceSmsMap relation_DeviceSmsMap);

    @Insert("insert into Relation_DeviceSmsMap (DeviceNo,EmployeeMobile,CreateDatetime) values (#{deviceNo},#{employeeMobile},#{createDatetime})")
    int insertRelation_DeviceSmsMap(Relation_DeviceSmsMap relation_DeviceSmsMap);

    @Update("update Relation_DeviceSmsMap set DeviceNo=#{deviceNo},EmployeeMobile=#{employeeMobile},CreateDatetime=#{createDatetime} where Id = #{id}")
    int updateRelation_DeviceSmsMap(Relation_DeviceSmsMap relation_DeviceSmsMap);

    @Delete("delete from Relation_DeviceSmsMap where Id=#{id}")
    int deleteRelation_DeviceSmsMap(Integer id);
}
