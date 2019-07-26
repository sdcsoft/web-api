package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.ProductUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Boiler_ProductUserMapper {

    @Select("select pu.* from Product_User pu where ProductId=#{productId} ")
    List<ProductUser> getProductUserListByProduct(@Param("productId") int productId);

    @Insert("insert into Product_User (UserId,ProductId) values (#{userId},#{productId})")
    void createProductUser(int userId, int productId);

    @Insert("<script>" +
            "insert into Product_User(UserId,ProductId) "
            + "values "
            + "<foreach collection =\"productUserList\" item=\"i\" index=\"index\" separator =\",\"> "
            + "(#{i.userId},#{i.productId}) "
            + "</foreach > " +
            "</script>")
    void createProductUserMap(@Param("productUserList") List<ProductUser> productUserList);

    @Delete("delete from Product_User where ProductId=#{productId}")
    void clearProductMap(int productId);

    @Delete("delete from Product_User where UserId=#{userId}")
    void clearUserMap(int userId);

    /**
     * 已产品为出发点，根据用户Id列表删除映射关系
     *
     * @param productId
     * @param deleteProductUserList
     */
    @Delete("<script>"
            + " delete from Product_User WHERE ProductId=#{productId} and UserId in "
            + "<foreach item='item' index='index' collection='deleteProductUserList' open='(' separator=',' close=')'>"
            + " #{item.userId} "
            + "</foreach>"
            + "</script>")
    void removeByProductAndUserList(int productId, @Param("deleteProductUserList") List<ProductUser> deleteProductUserList);

    /**
     * 已用户为出发点，根据用户Id列表删除映射关系
     *
     * @param userId
     * @param deleteProductUserList
     */
    @Delete("<script>"
            + " delete from Product_User WHERE UserId=#{userId} and ProductId in "
            + "<foreach item='item' index='index' collection='deleteProductUserList' open='(' separator=',' close=')'>"
            + " #{item.productId} "
            + "</foreach>"
            + "</script>")
    void removeByUserAndProductList(int userId, @Param("deleteProductUserList") List<ProductUser> deleteProductUserList);

}
