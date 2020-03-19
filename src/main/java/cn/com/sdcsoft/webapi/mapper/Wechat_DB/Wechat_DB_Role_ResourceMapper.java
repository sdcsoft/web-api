package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_Role_ResourceMapper {

    @Select("select * from Role_Resource where OpenId=#{openId}")
    List<Role_Resource> getRole_ResourceListByopenId(@Param("openId") String openId);

    @Select("select * from Role_Resource where DeviceNo=#{deviceNo}")
    List<Role_Resource> getRole_ResourceListByDeviceNo(@Param("deviceNo") String deviceNo);

    @Select("select * from Role_Resource where DeviceNo=#{deviceNo} and OpenId=#{openId}")
    List<Role_Resource> getRole_ResourceListByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo,@Param("openId") String openId);

    @Select("select * from Role_Resource where DeviceNo=#{deviceNo} and OpenId=#{openId} and ResId=#{resId}")
    Role_Resource getRole_ResourceListByitem(@Param("deviceNo") String deviceNo,@Param("openId") String openId,@Param("resId") String resId);

    @Insert("insert into Role_Resource(OpenId,ResId,DueTime) values (#{openId},#{resId},#{dueTime})")
    int insertRole_Resource(Role_Resource role_resource);

    @Insert("<script>" +
            "insert into Role_Resource(OpenId,ResId,DueTime,DeviceNo)"
            + "values "
            + "<foreach collection =\"rrList\" item=\"rr\" index=\"index\" separator =\",\"> "
            + "(#{rr.openId},#{rr.resId},#{rr.dueTime},#{rr.deviceNo})"
            + "</foreach > " +
            "</script>")
    int insertManyRole_Resource(@Param("rrList") List<Role_Resource> rrList);


    @Delete("delete from Role_Resource where ResId=#{resId}and OpenId=#{openId}")
    int deleteWxDevice(@Param("resId") String resId, @Param("openId") String openId);

    @Delete("delete from Role_Resource where Id=#{id}")
    int deleteWxDeviceById(@Param("id") String id);

    @Update("update Role_Resource set OpenId=#{openId},ResId=#{resId},DueTime=#{dueTime} where OpenId = #{openId} and DeviceNo = #{deviceNo} ")
    int updateRole_Resource(Role_Resource role_resource);

    @Update("update Role_Resource set OpenId=#{openId},ResId=#{resId},DueTime=#{dueTime} where Id = #{id}")
    int updateRole_ResourceById(Role_Resource role_resource);

}
