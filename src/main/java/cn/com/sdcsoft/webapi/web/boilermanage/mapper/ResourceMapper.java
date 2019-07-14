package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.Resource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResourceMapper {

    @Select("SELECT r.* FROM Resource_Org ro INNER JOIN Resource r  ON r.Id = ro.ResourceId WHERE ro.OrgId=#{orgId} order by Sort asc")
    List<Resource> getOrgResources(Integer orgId);

    @Select("select rs.* from `User` u  " +
            "INNER JOIN Role_Resource rr on u.RoleId = rr.RoleId " +
            "INNER JOIN Resource rs on rr.ResId = rs.Id  " +
            "where u.EmployeeId=#{employeeId} order by Sort asc")
    List<Resource> getUserResources(Integer employeeId);

    @Select("select re.* from Resource re " +
            "inner JOIN Role_Resource rr on rr.ResId = re.Id " +
            "where rr.RoleId=#{roleId} order by Sort asc")
    List<Resource> getRoleResources(Integer roleId);

    @Delete("delete from Role_Resource where RoleId=#{roleId}")
    void clearRoleResources(Integer roleId);

//    @Select("select r.* from Resource r " +
//            " inner JOIN Customer_Resource cr on cr.ResourceId=r.ResId " +
//            " LEFT JOIN Customer_User cu on cu.CustomerId=cr.CustomerId " +
//            "where cu.UserId=#{userId} order by Sort asc")
//    List<Resource> getOrgResources(Integer userId);

//    @Select("<script>select re.ResId from Resource re LEFT JOIN Role_Resource rr on rr.ResId = re.ResId where rr.RoleId=#{roleId} and re.PId<![CDATA[ <> ]]>0</script>")
//    List<Resource> getResourceResIdListByRoleId(@Param("roleId") Integer roleId);
//
//    @Select("select * from Resource where ResId=#{resId}")
//    Resource findOneById(@Param("resId") int resId);
}
