package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Resource_Product;
import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_Resource_ProductMapper {


    @Select("select * from Resource_Product")
    List<Resource_Product> getResource_ProductList();

    @Insert("insert into Resource_Product(ResourceId,RangeType,Price,Resource_Product.Range,ResourceName) values (#{resourceId},#{rangeType},#{price},#{range},#{resourceName})")
    int insertResource_ProductResource_Product(Resource_Product resource_product);

    @Delete("delete from Resource_Product where Id=#{id}")
    int deleteResource_Product(@Param("id") Integer id);

    @Update("update Resource_Product set ResourceId=#{resourceId},RangeType=#{rangeType},Price=#{price},Range=#{range},RangeResourceName=#{resourceName} where Id = #{id}")
    int updateResource_Product(Resource_Product resource_product);
}
