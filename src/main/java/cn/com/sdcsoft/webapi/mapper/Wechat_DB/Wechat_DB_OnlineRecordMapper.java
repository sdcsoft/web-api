package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.OnlineRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_OnlineRecordMapper {


    @Insert("insert into OnlineRecord (MobileNo,BeginDatetime,EndDatetime,Minutes) values (#{mobileNo},#{beginDatetime},#{endDatetime},#{minutes})")
    int insertOnlineRecord(OnlineRecord onlineRecord);

}
