package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.AddDeviceRecord;
import cn.com.sdcsoft.webapi.wechat.entity.OnlineRecord;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

@Component
public interface Wechat_DB_AddDeviceRecordMapper {


    @Insert("insert into AddDeviceRecord (MobileNo,DeviceNo) values (#{mobileNo},#{deviceNo})")
    int insertAddDeviceRecord(AddDeviceRecord addDeviceRecord);

}
