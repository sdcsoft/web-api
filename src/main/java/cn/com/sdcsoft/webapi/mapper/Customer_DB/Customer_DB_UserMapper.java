package cn.com.sdcsoft.webapi.mapper.Customer_DB;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.User;
import cn.com.sdcsoft.webapi.web.entity.OrgUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Customer_DB_UserMapper {

    @Select("select * from User where OrgId =#{orgId}")
    List<User> findAll(@Param("orgId") Integer orgId);

    @Select("select * from User where EmployeeId=#{employeeId}")
    User findUserByEmployeeId(@Param("employeeId") Integer employeeId);

    @Select("select * from User where Id=#{userId}")
    User findUserById(@Param("userId") Integer userId);

    @Select("select * from User where OpenId=#{openId}")
    User findUserByOpenId(@Param("openId") String openId);

    @Update("update User set UserName=#{userName},Mark=#{mark} where Id=#{id}")
    void modifyUser(User user);

    @Update("update User set RoleId=#{roleId},RoleName=#{roleName} where Id=#{userId}")
    void changeUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId, @Param("roleName") String roleName);

    @Delete("delete from User where Id=#{id}")
    void removeUser(@Param("id") Integer id);

    @Insert("insert into User (OpenId,OrgId,EmployeeId,UserName) values (#{invCode},#{orgId},0,NULL)")
    void createInvCode(String invCode,Integer orgId);

    @Update("update User set OpenId=#{openId},EmployeeId=#{employeeId},UserName=#{userName},Mark=#{mobile} where OpenId=#{invCode}")
    int createUser(User user);

    @Insert("insert into User (OpenId,OrgId,EmployeeId,UserName,RoleId,RoleName,Mark) values (#{openId},#{orgId},#{employeeId},#{userName},1,'系统管理员','系统内置管理员，不能被删除')")
    void createAdmin(OrgUser user);
}
