package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.User;
import cn.com.sdcsoft.webapi.web.entity.OrgUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Boiler_UserMapper {

    @Select("select * from User where OrgId =#{orgId}")
    List<User> findAll(@Param("orgId") Integer orgId);

    @Select("select * from User where EmployeeId=#{employeeId}")
    User findUserByEmployeeId(@Param("employeeId") Integer employeeId);

    @Select("select * from User where Id=#{userId}")
    User findUserById(@Param("userId") Integer userId);

    @Update("update User set UserName=#{userName},Mark=#{mark} where Id=#{id}")
    void modifyUser(User user);

    @Update("update User set RoleId=#{roleId},RoleName=#{roleName} where Id=#{userId}")
    void changeUserRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId,@Param("roleName") String roleName);

    @Delete("delete from User where Id=#{id}")
    void removeUser(@Param("id") Integer id);

    @Insert("insert into User (OrgId,EmployeeId,UserName) values (#{orgId},#{id},#{realName})")
    void createUser(OrgUser user);
    @Insert("insert into User (OrgId,EmployeeId,UserName,RoleId,RoleName,Mark) values (#{orgId},#{id},#{realName},1,'系统管理员','系统内置管理员，不能被删除')")
    void createAdmin(OrgUser user);
}
