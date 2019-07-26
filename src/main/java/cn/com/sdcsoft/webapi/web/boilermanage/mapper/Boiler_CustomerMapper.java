package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.Customer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Boiler_CustomerMapper {

    @Select("select * from Customer where OrgId=#{orgId}")
    List<Customer> find(@Param("orgId") int orgId);

    @Select("<script>" +
            "select * from Customer " +
            "<where>" +
            "OrgId=#{orgId} " +
            "<if test='name!= null and name.length>0 '> " +
            " AND Name LIKE CONCAT('%',#{name},'%')" +
            "</if>" +
            "</where>" +
            "</script>")
    List<Customer> search(@Param("orgId") int orgId, @Param("name") String name);

    @Select("select * from Customer where Id=#{id} and OrgId=#{orgId}")
    Customer getCustomerById(@Param("orgId") int orgId, @Param("id") int id);

    @Update("update Customer set Name=#{name},Phone=#{phone},Province=#{province},City=#{city},District=#{district},Address=#{address} where Id = #{id} and OrgId=#{orgId}")
    void modifyCustomer(Customer boilerCustomer);

    @Update("update Customer set Phone=#{phone},Province=#{province},City=#{city},District=#{district},Address=#{address} where Id = #{id} and OrgId=#{orgId}")
    void modifyCustomerExtendsInfo(Customer boilerCustomer);

    @Select("select count(*) from Customer where OrgId=#{orgId} and Name=#{name}")
    int checkExist(Customer boilerCustomer);

    @Insert("insert into Customer (Name,Phone,Province,City,District,Address,OrgId) values (#{name},#{phone},#{province},#{city},#{district},#{address},#{orgId})")
    void createCustomer(Customer boilerCustomer);

    @Select("select count(*) from Product where CustomerId=#{customerId}")
    int checkProductExist(@Param("customerId") Integer customerId);

    @Delete("delete from Customer where Id=#{id} and OrgId=#{orgId}")
    void removeCustomer(@Param("orgId") int orgId,@Param("id") int id);
}
