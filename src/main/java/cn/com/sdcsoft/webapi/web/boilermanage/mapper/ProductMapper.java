package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.Product;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.ProductTypeAmountClass;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductMapper {

    @Select("<script>" +
            "select pt.* from Product pt "+
            "inner join Product_Category pc on pt.ProductCategoryId= pc.Id "+
            "left join Customer c on pt.CustomerId=c.Id "+
            "<where>" +
            "pt.OrgId=#{orgId} " +
            "<if test='product.customerId != null'> " +
            "AND pt.CustomerId = #{product.customerId}" +
            "</if>" +
            "<if test='product.customerName != null and product.customerName.length > 0'> " +
            "AND c.Name LIKE CONCAT('%',#{product.customerName},'%')" +
            "</if>" +
            "<if test='product.saleDate != null'> " +
            " AND SaleDate=DATE_FORMAT(DATE_ADD(#{product.saleDate},INTERVAL 1 DAY), '%Y-%m-%d') " +
            "</if>" +
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
            " order by EditDateTime desc" +
            "</script>")
    List<Product> searchForAdmin(@Param("orgId")Integer orgId, @Param("product") Product product);

    @Select("<script>" +
            "select pt.* from Product_User pu" +
            "inner join Product pt on pu.ProductId=pt.Id"+
            "inner join Product_Category pc on pt.ProductCategoryId= pc.Id "+
            "left join Customer c on pt.CustomerId=c.Id"+
            "<where>" +
            "pu.UserId=#{product.userId} " +
            "<if test='product.customerId != null'> " +
            "AND pt.CustomerId = #{product.customerId}" +
            "</if>" +
            "<if test='product.saleDate != null'> " +
            " AND SaleDate=DATE_FORMAT(DATE_ADD(#{product.saleDate},INTERVAL 1 DAY), '%Y-%m-%d') " +
            "</if>" +
            "<if test='product.boilerNo != null and product.boilerNo.length>0'> " +
            " AND BoilerNo LIKE CONCAT('%',#{product.boilerNo},'%')" +
            "</if>" +
            "<if test='product.productCategoryId != null'> " +
            " AND ProductCategoryId=#{product.productCategoryId}" +
            "</if>" +
            "<if test='product.controllerNo != null and product.controllerNo.length>0'> " +
            " AND ControllerNo LIKE CONCAT('%',#{product.controllerNo},'%')" +
            "</if>" +
            "<if test='product.tonnageNum != null'> " +
            " AND TonnageNum=#{product.tonnageNum}" +
            "</if>" +
            "<if test='product.media != null'> " +
            " AND Media=#{product.media}" +
            "</if>" +
            "<if test='product.power != null'> " +
            " AND Power=#{product.power}" +
            "</if>" +
            "</where>" +
            " order by EditDateTime desc" +
            "</script>")
    List<Product> search(@Param("userId")Integer userId, @Param("product") Product product);

    @Select("<script>" +
            "select * from Product_User pu" +
            "inner join Product pt on pu.ProductId=pt.Id"+
            "inner join Product_Category pc on pt.ProductCategoryId= pc.Id "+
            "left join Customer c on pt.CustomerId=c.Id"+
            "<where> pu.UserId=#{userId} </where>" +
            "order by EditDateTime desc" +
            "</script>")
    List<Product> find(@Param("userId") int userId);

//    @Select("select * from Product where BoilerNo=#{boilerNo} and ControllerNo=#{controllerNo}")
//    Product getProductByBoilerNo(@Param("userId") int userId,@Param("boilerNo") String boilerNo, @Param("controllerNo") String controllerNo);

    @Select("SELECT COUNT(*) AS Amount,case Media WHEN 0 THEN '热水' when 1 then '蒸汽' when 2 then '导热油' when 3 then '热风' ELSE '真空' END as mediaType,case Power WHEN 0 THEN '燃油气' when 1 then '电' when 2 then '煤' when 3 then '生物质' ELSE '余热' END powerType FROM (SELECT ControllerNo,Media,Power FROM Product INNER JOIN Product_User ON Product.Id = Product_User.ProductId WHERE UserId = #{userId}) AS tb GROUP BY Media,Power")
    List<ProductTypeAmountClass> getProductTypeAmountByUserId(@Param("userId") int userId);

    @Insert("INSERT into Product(OrgId,BoilerNo,ProductCategoryId,ControllerNo,TonnageNum,Media,Power,IsSell,SaleDate,Longitude,Latitude,Province,City,District,Street,CreateDateTime,EditDateTime,CustomerId,CustomerName) " +
            " VALUES(#{orgId},#{boilerNo},#{productCategoryId},#{controllerNo},#{tonnageNum},#{media},#{power},#{isSell},DATE_FORMAT(DATE_ADD(#{saleDate},INTERVAL 1 DAY), '%Y-%m-%d'),#{longitude},#{latitude},#{province},#{city}," +
            "#{district},#{street},#{createDateTime},#{editDateTime},#{customerId},#{customerName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createProduct(Product product);

    @Update("update Product set BoilerNo=#{boilerNo},ProductCategoryId=#{productCategoryId}, TonnageNum=#{tonnageNum},Media=#{media}," +
            "Power=#{power},IsSell=#{isSell},SaleDate=DATE_FORMAT(DATE_ADD(#{saleDate},INTERVAL 1 DAY), '%Y-%m-%d'),Longitude=#{longitude},Latitude=#{latitude},Province=#{province},City=#{city}," +
            "District=#{district},Street=#{street},EditDateTime=#{editDateTime},CustomerId=#{customerId}" +
            " where Id=#{id}")
    void modifyProductInfo(Product Product);

    @Update("update Product set IsSell=#{isSell},SaleDate=DATE_FORMAT(DATE_ADD(#{saleDate},INTERVAL 1 DAY), '%Y-%m-%d'),Longitude=#{longitude},Latitude=#{latitude},Province=#{province},City=#{city}," +
            "District=#{district},Street=#{street},EditDateTime=#{editDateTime},CustomerId=#{customerId},CustomerName=#{customerName} where Id=#{id}")
    void modifyProductSellInfo(Product product);

    @Delete("delete from Product where Id =#{id}")
    void removerProduct(int id);
}
