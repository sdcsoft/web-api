package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    @Select("select * from User where OrgId =#{orgId}")
    List<User> findAll(@Param("orgId") Integer orgId);

    @Select("select * from User where EmployeeId=#{employeeId}")
    User findUserByEmployeeId(@Param("employeeId") Integer employeeId);

    @Select("select * from User where Id=#{id}")
    User findUserById(@Param("id") Integer id);

    @Update("update User set UserName=#{userName},RoleId=#{roleId},Mark=#{Mark} where Id=#{id}")
    void modifyUser(User user);

    @Update("update User set RoleId=#{roleId},RoleName=#{roleName} where Id=#{id}")
    void changeUserRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId,@Param("roleName") String roleName);

    @Delete("delete from User where Id=#{id}")
    void removeUser(@Param("id") Integer id);
}
