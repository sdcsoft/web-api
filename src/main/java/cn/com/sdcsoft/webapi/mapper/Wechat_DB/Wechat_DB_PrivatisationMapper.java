package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Privatisation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_PrivatisationMapper {

    @Select("select * from Privatisation where Id=#{id}")
    Privatisation getPrivatisationListById(@Param("id") Integer id);

    @Select("select * from Privatisation where DeviceNo=#{deviceNo} And OpenId=#{openId}")
    List<Privatisation> getPrivatisationListByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo,@Param("openId") String openId);

    @Select("select * from Privatisation where DeviceNo=#{deviceNo} And OpenId=#{openId}")
    Privatisation getPrivatisationByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo,@Param("openId") String openId);

    @Select("select * from Privatisation where BuyersOpenId=#{buyersOpenId}")
    List<Privatisation> getPrivatisationListBybuyersOpenId(@Param("buyersOpenId") String buyersOpenId);

    @Select("select * from Privatisation where DeviceNo=#{deviceNo} ")
    List<Privatisation> findPrivatisationByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo);

    @Insert("<script>" +
            "insert into Privatisation(BuyersOpenId,OpenId,DueTime,DeviceNo)"
            + "values "
            + "<foreach collection =\"rrList\" item=\"rr\" index=\"index\" separator =\",\"> "
            + "(#{rr.buyersOpenId},#{rr.openId},#{rr.dueTime},#{rr.deviceNo})"
            + "</foreach > " +
            "</script>")
    int insertManyPrivatisation(@Param("rrList") List<Privatisation> rrList);




    @Delete("delete from Privatisation where Id=#{id}")
    int deletePrivatisationById(@Param("id") String id);

    @Update("update Privatisation set DeviceNo=#{deviceNo},OpenId=#{openId},Mobile=#{mobile} ,DueTime=#{dueTime} ,RealName=#{realName} ,BuyersOpenId=#{buyersOpenId} where Id = #{id} ")
    int updatePrivatisation(Privatisation privatisation);

    @Update("update Privatisation set DueTime=#{dueTime}  where Id = #{id}")
    int updatePrivatisationDueTime(Privatisation privatisation);

}
