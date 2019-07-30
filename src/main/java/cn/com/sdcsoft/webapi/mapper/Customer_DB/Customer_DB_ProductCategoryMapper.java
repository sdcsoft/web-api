package cn.com.sdcsoft.webapi.mapper.Customer_DB;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.ProductCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Customer_DB_ProductCategoryMapper {

    @Select("select * from Product_Category where OrgId=#{orgId}")
    List<ProductCategory> findProductCategory(@Param("orgId") int orgId);

    @Select("select * from Product_Category where Name=#{name} and OrgId=#{orgId} ")
    ProductCategory findOne(@Param("name") String name, @Param("orgId") int orgId);

    @Update("update Product_Category set Name=#{name} where Id = #{id}")
    void modifyProductCategory(ProductCategory BoilerModel);

    @Insert("insert into Product_Category (Name,OrgId,Sort) values (#{name},#{orgId},#{sort})")
    void createProductCategory(ProductCategory productCategory);

    @Select("select count(*) from Product where ProductCategoryId=#{productCategoryId}")
    int checkProductCountOfTheCategory(@Param("productCategoryId") int productCategoryId);

    @Delete("delete from Product_Category where Id=#{id}")
    void removeById(Integer id);


}
