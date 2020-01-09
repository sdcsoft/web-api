package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_Role_ResourceMapper {

    @Select("select * from Role_Resource where OpenId=#{openId}")
    List<Role_Resource> getRole_ResourceListByopenId(@Param("openId") String openId);


    @Insert("insert into Role_Resource(OpenId,ResId,DueTime) values (#{openId},#{resId},#{dueTime})")
    int insertRole_Resource(Role_Resource role_resource);

    @Insert("<script>" +
            "insert into Role_Resource(OpenId,ResId,DueTime)"
            + "values "
            + "<foreach collection =\"rrList\" item=\"rr\" index=\"index\" separator =\",\"> "
            + "(#{rr.openId},#{rr.resId},#{rr.dueTime})"
            + "</foreach > " +
            "</script>")
    int insertManyRole_Resource(@Param("rrList") List<Role_Resource> rrList);


    @Delete("delete from Role_Resource where ResId=#{resId}and OpenId=#{openId}")
    int deleteWxDevice(@Param("resId") String resId, @Param("openId") String openId);

    @Update("update Role_Resource set OpenId=#{openId},ResId=#{resId},DueTime=#{dueTime} where OpenId = #{openId} and DeviceNo = #{deviceNo} ")
    int updateRole_Resource(Role_Resource role_resource);
}
