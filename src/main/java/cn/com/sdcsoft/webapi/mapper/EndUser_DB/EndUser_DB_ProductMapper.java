package cn.com.sdcsoft.webapi.mapper.EndUser_DB;

import cn.com.sdcsoft.webapi.entity.SoldProductSearchOptions;
import cn.com.sdcsoft.webapi.web.endusermanage.entity.ProductTypeAmountClass;
import cn.com.sdcsoft.webapi.web.endusermanage.entity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EndUser_DB_ProductMapper {

    @Select("<script>" +
            "select pt.* from Product pt " +
            "<where>" +
            "pt.OrgId=#{orgId} " +
            "<if test='product.boilerNo != null and product.boilerNo.length>0'> " +
            " AND BoilerNo LIKE CONCAT('%',#{product.boilerNo},'%') " +
            "</if>" +
            "<if test='product.controllerNo != null and product.controllerNo.length>0'> " +
            " AND ControllerNo LIKE CONCAT('%',#{product.controllerNo},'%') " +
            "</if>" +
            "<if test='product.tonnageNum != null'> " +
            " AND TonnageNum=#{product.tonnageNum} " +
            "</if>" +
            "<if test='product.media != null'> " +
            " AND Media=#{product.media} " +
            "</if>" +
            "<if test='product.power != null'> " +
            " AND Power=#{product.power} " +
            "</if>" +
            "</where>" +
            " order by CreateDateTime desc" +
            "</script>")
    List<Product> searchForAdmin(@Param("orgId") Integer orgId, @Param("product") Product product);



    @Select("<script>" +
            "select pt.* from Product_User pu" +
            "inner join Product pt on pu.ProductId=pt.Id" +
            "<where>" +
            "pu.UserId=#{userId} " +
            "<if test='product.boilerNo != null and product.boilerNo.length>0'> " +
            " AND BoilerNo LIKE CONCAT('%',#{product.boilerNo},'%') " +
            "</if>" +
            "<if test='product.productCategoryId != null'> " +
            " AND ProductCategoryId=#{product.productCategoryId} " +
            "</if>" +
            "<if test='product.controllerNo != null and product.controllerNo.length>0'> " +
            " AND ControllerNo LIKE CONCAT('%',#{product.controllerNo},'%') " +
            "</if>" +
            "<if test='product.tonnageNum != null'> " +
            " AND TonnageNum=#{product.tonnageNum} " +
            "</if>" +
            "<if test='product.media != null'> " +
            " AND Media=#{product.media} " +
            "</if>" +
            "<if test='product.power != null'> " +
            " AND Power=#{product.power} " +
            "</if>" +
            "</where>" +
            " order by CreateDateTime desc" +
            "</script>")
    List<Product> search(@Param("userId") Integer userId, @Param("product") Product product);


    @Select("<script>select pt.* from Product pt " +
            "<where>" +
            "pt.OrgId=#{orgId}" +
            "<if test='searchOptions.categoryId != null'> " +
            " AND pt.ProductCategoryId=#{searchOptions.categoryId} " +
            "</if>" +
            "<if test='searchOptions.power != null'> " +
            " AND pt.Power=#{searchOptions.power} " +
            "</if>" +
            "<if test='searchOptions.media != null'> " +
            " AND pt.Media=#{searchOptions.media} " +
            "</if>" +
            "<if test='searchOptions.boilerNo != null and searchOptions.boilerNo.length>0'> " +
            " AND pt.BoilerNo LIKE CONCAT('%',#{searchOptions.boilerNo},'%') " +
            "</if>" +
            "</where>" +
            "order by CreateDateTime desc</script>")
    List<Product> findSoldForAdmin(@Param("orgId") Integer orgId, @Param("searchOptions") SoldProductSearchOptions searchOptions);

    @Select("<script>" +
            "select * from Product_User pu" +
            "inner join Product pt on pu.ProductId=pt.Id" +
            "<where> pu.UserId=#{userId} " +
            "<if test='searchOptions.categoryId != null'> " +
            " AND pt.ProductCategoryId=#{searchOptions.categoryId} " +
            "</if>" +
            "<if test='searchOptions.power != null'> " +
            " AND pt.Power=#{searchOptions.power} " +
            "</if>" +
            "<if test='searchOptions.media != null'> " +
            " AND pt.Media=#{searchOptions.media} " +
            "</if>" +
            "<if test='searchOptions.boilerNo != null and searchOptions.boilerNo.length>0'> " +
            " AND pt.BoilerNo LIKE CONCAT('%',#{searchOptions.boilerNo},'%') " +
            "</if>" +
            "</where>" +
            "order by CreateDateTime desc" +
            "</script>")
    List<Product> find(@Param("userId") int userId, @Param("searchOptions") SoldProductSearchOptions searchOptions);

    @Select("SELECT COUNT(*) AS Amount,case Media WHEN 0 THEN '热水' when 1 then '蒸汽' when 2 then '导热油' when 3 then '热风' ELSE '真空' END as mediaType,case Power WHEN 0 THEN '燃油气' when 1 then '电' when 2 then '煤' when 3 then '生物质' ELSE '余热' END powerType FROM (SELECT ControllerNo,Media,Power FROM Product INNER JOIN Product_User ON Product.Id = Product_User.ProductId WHERE UserId = #{userId}) AS tb GROUP BY Media,Power")
    List<ProductTypeAmountClass> getProductTypeAmountByUserId(@Param("userId") int userId);

    @Insert("INSERT into Product(OrgId,BoilerNo,NickName,ControllerNo,TonnageNum,Media,Power,CreateDateTime) " +
            " VALUES(#{orgId},#{boilerNo},#{nickName},#{controllerNo},#{tonnageNum},#{media},#{power}#{createDateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createProduct(Product product);

    @Update("update Product set BoilerNo=#{boilerNo},NickName=#{nickName},TonnageNum=#{tonnageNum},Media=#{media}," +
            "Power=#{power}" +
            " where Id=#{id}")
    void modifyProductInfo(Product Product);

//    @Update("update Product set IsSell=#{isSell},SaleDate=DATE_FORMAT(DATE_ADD(#{saleDate},INTERVAL 1 DAY), '%Y-%m-%d'),Longitude=#{longitude},Latitude=#{latitude},Province=#{province},City=#{city}," +
//            "District=#{district},Street=#{street} where Id=#{id}")
//    void modifyProductSellInfo(Product product);

    @Delete("delete from Product where Id =#{id}")
    void removerProduct(int id);
}