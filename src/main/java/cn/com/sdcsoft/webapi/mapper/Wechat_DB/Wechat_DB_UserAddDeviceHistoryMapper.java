package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.UserAddDeviceHistory;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

@Component
public interface Wechat_DB_UserAddDeviceHistoryMapper {


    @Insert("insert into UserAddDeviceHistory (OpenId,DeviceNo) values (#{openId},#{deviceNo})")
    int insertUserAddDeviceHistory(UserAddDeviceHistory userAddDeviceHistory);

}
