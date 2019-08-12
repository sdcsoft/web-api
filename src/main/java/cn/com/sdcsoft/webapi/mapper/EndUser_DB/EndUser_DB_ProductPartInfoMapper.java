package cn.com.sdcsoft.webapi.mapper.EndUser_DB;

import cn.com.sdcsoft.webapi.web.endusermanage.entity.ProductPartInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EndUser_DB_ProductPartInfoMapper {

    @Select("select i.*,c.Name PartCategoryName,s.Name PartSubCategoryName from Product_Part_Info i " +
            "inner join Part_Category c on i.PartCategoryId=c.id " +
            "inner join Part_SubCategory s on i.PartSubCategoryId=s.id  " +
            "where ProductId = #{productId}")
    List<ProductPartInfo> findByProductId(int productId);

    @Insert("<script>"+
            "insert into Product_Part_Info(ProductId,PartCategoryId,PartSubCategoryId,BrandName,ModelName,AmountOfUser,Supplier,Remarks)"
            + "values "
            + "<foreach collection =\"productPartInfos\" item=\"i\" index=\"index\" separator =\",\"> "
            + "(#{i.productId},#{i.partCategoryId},#{i.partSubCategoryId},#{i.brandName},#{i.modelName},#{i.amountOfUser},#{i.supplier},#{i.remarks}) "
            + "</foreach > " +
            "</script>")
    void createProductPartInfoList(@Param("productPartInfos") List<ProductPartInfo> productPartInfos);

    @Update("update Product_Part_Info " +
            "set PartCategoryId=#{partCategoryId}," +
            "PartSubCategoryId=#{partSubCategoryId}," +
            "BrandName=#{brandName}," +
            "ModelName=#{modelName}," +
            "AmountOfUser=#{amountOfUser}," +
            "Supplier=#{supplier}," +
            "Remarks=#{remarks} " +
            "where Id=#{id}")
    void modifyProductPartInfo(ProductPartInfo partInfo);

    @Delete("delete from Product_Part_Info where ProductId=#{productId} and Id=#{partId}")
    void removeProductPartInfo(@Param("productId") Integer productId, @Param("partId") Integer partId);

    @Delete("delete from Product_Part_Info where ProductId=#{productId}")
    void clearProductPartInfos(Integer productId);
}
