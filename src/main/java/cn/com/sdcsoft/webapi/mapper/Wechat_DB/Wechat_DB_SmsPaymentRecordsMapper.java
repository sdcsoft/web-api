package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.SmsPaymentRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_SmsPaymentRecordsMapper {



    @Select("select * from SmsPaymentRecords where DeviceNo=#{deviceNo} and OpenId=#{openId}")
    List<SmsPaymentRecords> getSmsPaymentRecordsListByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo, @Param("openId") String openId);

    @Select("select * from SmsPaymentRecords where DeviceNo=#{deviceNo}")
    SmsPaymentRecords getSmsPaymentRecordsListByDeviceNo(@Param("deviceNo") String deviceNo);

    @Select("select * from SmsPaymentRecords where DeviceNo=#{deviceNo} and IMEI=#{iMEI}")
    SmsPaymentRecords getSmsPaymentRecordsListByDeviceNoAndIMEI(@Param("deviceNo") String deviceNo, @Param("iMEI") String iMEI);

    @Select("<script>" +
            "select ee.* from SmsPaymentRecords ee" +
            "<where>" +
            " 1=1 " +
            "<if test='smsPaymentRecords.deviceNo != null and smsPaymentRecords.deviceNo.length>0 '> " +
            " AND DeviceNo=#{smsPaymentRecords.deviceNo} " +
            "</if>" +
            "<if test='smsPaymentRecords.openId != null and smsPaymentRecords.openId.length>0 '> " +
            " AND OpenId=#{smsPaymentRecords.openId} " +
            "</if>" +
            "<if test='smsPaymentRecords.iMEI != null and smsPaymentRecords.iMEI.length>0 '> " +
            " AND IMEI=#{smsPaymentRecords.iMEI} " +
            "</if>" +
            "<if test='smsPaymentRecords.status != null '> " +
            " AND Status=#{smsPaymentRecords.status} " +
            "</if>" +
            "</where>" +
            "</script>")
    List<SmsPaymentRecords> getSmsPaymentRecordsListByCondition(@Param("smsPaymentRecords")SmsPaymentRecords smsPaymentRecords);




    @Insert("insert into SmsPaymentRecords(DeviceNo,OpenId,IMEI,DueTime,Status) values (#{deviceNo},#{openId},#{iMEI},#{dueTime},#{status})")
    int insertSmsPaymentRecords(SmsPaymentRecords smsPaymentRecords);

    @Update("update SmsPaymentRecords set DeviceNo=#{deviceNo},OpenId=#{openId},IMEI=#{iMEI},DueTime=#{dueTime},Status=#{status} where Id = #{id}")
    int updateSmsPaymentRecords(SmsPaymentRecords smsPaymentRecords);

}
