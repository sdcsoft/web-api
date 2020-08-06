package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.UserAddDeviceHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Wechat_DB_UserAddDeviceHistoryMapper {


    @Insert("insert into UserAddDeviceHistory (OpenId,DeviceNo,BindTime) values (#{openId},#{deviceNo},#{bindTime})")
    int insertUserAddDeviceHistory(UserAddDeviceHistory userAddDeviceHistory);

    @Select("select * from UserAddDeviceHistory where DeviceNo=#{deviceNo} and OpenId=#{openId}")
    List<UserAddDeviceHistory> getUserAddDeviceHistoryListByDeviceNoAndOpenId(@Param("deviceNo") String deviceNo, @Param("openId") String openId);

}
