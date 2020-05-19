package cn.com.sdcsoft.webapi.mapper.Enterprise_DB;

import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.RepairInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public interface Enterprise_DB_RepairInfoMapper {

    @Select("select r.*,p.ControllerNo from Repair r inner join Product p on r.productId=p.Id where productId = #{productId}")
    List<RepairInfo> getProductRepairInfos(Integer productId);

    @Select("select r.*,p.ControllerNo from Repair r inner join Product p on r.productId=p.Id where UserId = #{userId}")
    List<RepairInfo> getUserRepairInfos(Integer userId);

    @Select("select r.*,p.ControllerNo from Repair r inner join Product p on r.productId=p.Id where UserId = #{userId} " +
            "and RepairDatetime between #{startTime} and #{endTime}")
    List<RepairInfo> getUserRepairInfosByDate(@Param("userId") Integer userId, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    @Select("select * from Repair where productId = #{productId} and RepairDatetime between #{startTime} and #{endTime}")
    List<RepairInfo> getProductRepairInfosByDate(@Param("productId") Integer productId, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    @Insert(
            "insert into Repair(RepairDatetime,RepairContent,UserId,UserName,ProductId,CreateUserId,CreateUserName,CreateDatetime)"
                    + "values "
                    + "(#{repairDatetime},#{repairContent},#{userId},#{userName},#{productId},#{createUserId},#{createUserName},#{createDatetime}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertRepairInfo(RepairInfo RepairInfo);

    @Delete("delete from Repair where Id=#{id}")
    void removeRepairInfoById(int id);

}
