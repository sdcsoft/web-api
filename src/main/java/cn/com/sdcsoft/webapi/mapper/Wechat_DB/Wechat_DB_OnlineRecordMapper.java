package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.OnlineRecord;
import cn.com.sdcsoft.webapi.wechat.entity.TypeResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_OnlineRecordMapper {


    @Insert("insert into OnlineRecord (OpenId,BeginDatetime,EndDatetime,Minutes) values (#{openId},#{beginDatetime},#{endDatetime},#{minutes})")
    int insertOnlineRecord(OnlineRecord onlineRecord);


    @Select("select OpenId,count(*) AS count,SUM(Minutes)as sum  from OnlineRecord GROUP BY OpenId ORDER BY  sum DESC")
    List<TypeResult> OnlineRecordByUser();

    @Select("select * from OnlineRecord  where OpenId=#{openId}")
    List<OnlineRecord> OnlineRecordByOpenId(@Param("openId") String openId);


}
