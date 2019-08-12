package cn.com.sdcsoft.webapi.mapper.EndUser_DB;

import cn.com.sdcsoft.webapi.web.endusermanage.entity.Resource;
import cn.com.sdcsoft.webapi.web.entity.OrgResource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EndUser_DB_ResourceMapper {

    @Select("SELECT r.* FROM Org_Resource ro INNER JOIN Resource r  ON r.Id = ro.ResourceId WHERE ro.OrgId=#{orgId} order by Sort asc")
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

    @Insert("insert into Resource (ResName,PId,URL,PageUrl,Hidden,Permission,Sort) values (#{resName},#{pId},#{url},#{pageUrl},#{hidden},#{permission},#{sort})")
    void createResource(Resource Resource);

    @Select("select * from Resource")
    List<Resource> list();

    @Update("update Resource set ResName=#{resName},PId=#{pId} ,URL=#{url},PageUrl=#{pageUrl},Hidden=#{hidden},Sort=#{sort} where Id=#{id}")
    void modifyResource(Resource Resource);

    @Delete("delete from Org_Resource where OrgId=#{orgId}")
    void clearOrgResources(Integer orgId);

    @Insert("<script>" +
            "insert into Org_Resource (OrgId,ResourceId)"
            + "values "
            + "<foreach collection =\"orgResources\" item=\"i\" index=\"index\" separator =\",\"> "
            + "(#{i.orgId},#{i.resId}) "
            + "</foreach > " +
            "</script>")
    void createOrgResourceMap(@Param("orgResources") List<OrgResource> orgResources);

}
