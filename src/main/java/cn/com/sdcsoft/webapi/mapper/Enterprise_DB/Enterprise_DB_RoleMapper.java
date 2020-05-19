package cn.com.sdcsoft.webapi.mapper.Enterprise_DB;

import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.Role;
import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.RoleResource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Enterprise_DB_RoleMapper {

    @Select("select * from Role where OrgId = #{orgId}")
    List<Role> list(Integer orgId);

    @Update("update Role set roleName=#{roleName},RoleDesc=#{roleDesc} where Id = #{id}")
    void modifyRole(Role role);

    @Update("update Role set RoleDesc=#{roleDesc} where Id = #{id}")
    void modifyRoleExtendsInfo(Role role);

    @Update("update User set RoleName=#{roleName} where RoleId=#{id}")
    void changeUsersRoleInfo(Role role);

    @Insert("insert into Role (OrgId,RoleName,RoleDesc) values (#{orgId},#{roleName},#{roleDesc})")
    void createRole(Role role);

    @Delete("delete from Role where Id=#{roleId} and OrgId=#{orgId}")
    void removeRole(@Param("orgId") Integer orgId, @Param("roleId") Integer roleId);

    @Insert("<script>" +
            "insert into Role_Resource (RoleId,ResId)"
            + "values "
            + "<foreach collection =\"roleResources\" item=\"i\" index=\"index\" separator =\",\"> "
            + "(#{i.roleId},#{i.resId}) "
            + "</foreach > " +
            "</script>")
    void createRoleResourceMap(@Param("roleResources") List<RoleResource> roleResources);

    @Select("select count(*) from Role where OrgId=#{orgId} and RoleName=#{roleName}")
    int checkExist(Role role);

    @Select("select * from Role where OrgId=#{orgId} and Id=#{id}")
    Role findRole(Role role);

    @Select("select count(*) from User where RoleId=#{roleId}")
    int checkUsersInRole(@Param("roleId") Integer roleId);

    @Delete("Delete from Role_Resource  where RoleId=#{roleId}")
    void clearRoleResourceMap(@Param("roleId") Integer roleId);


}
