package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.DeviceOnlineRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_DeviceOnlineRecordMapper {


    @Insert("insert into DeviceOnlineRecord (OpenId,BeginDatetime,EndDatetime,Minutes,DeviceNo) values (#{openId},#{beginDatetime},#{endDatetime},#{minutes},#{deviceNo})")
    int insertDeviceOnlineRecord(DeviceOnlineRecord deviceOnlineRecord);

    @Select("select * from DeviceOnlineRecord where DeviceNo=#{deviceNo} and OpenId=#{openId}")
    List<DeviceOnlineRecord> getDeviceOnlineRecordListByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo, @Param("openId") String openId);

}
