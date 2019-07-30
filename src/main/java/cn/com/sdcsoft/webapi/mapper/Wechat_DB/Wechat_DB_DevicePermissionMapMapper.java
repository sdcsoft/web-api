package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Relation_DevicePermissionMap;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_DevicePermissionMapMapper {

    @Select("select * from Relation_DevicePermissionMap where OpenId=#{OpenId}")
    List<Relation_DevicePermissionMap> getRelation_DevicePermissionMapListByOpenId(@Param("OpenId") String OpenId);
    @Select("<script>" +
            "select * from Relation_DevicePermissionMap "+
            "</script>")
    List<Relation_DevicePermissionMap> getRelation_DevicePermissionMapListByCondition(Relation_DevicePermissionMap relation_devicePermissionMap);



    @Insert("insert into Relation_DevicePermissionMap (EmployeeMobile,OpenId,CreateDatetime) values (#{employeeMobile},#{openId},#{createDatetime})")
    int insertRelation_DevicePermissionMap(Relation_DevicePermissionMap relation_devicePermissionMap);

    @Update("update Relation_DevicePermissionMap set EmployeeMobile=#{employeeMobile},OpenId=#{openId},CreateDatetime=#{createDatetime} where Id = #{id}")
    int updateRelation_DevicePermissionMap(Relation_DevicePermissionMap relation_devicePermissionMap);

    @Delete("delete from Relation_DevicePermissionMap where Id=#{id}")
    int deleteRelation_DevicePermissionMap(Integer id);
}
