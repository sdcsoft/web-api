package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Monitoring;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface Wechat_DB_MonitoringMapper {

    @Select("select * from Monitoring  where DeviceNo=#{deviceNo}")
    List<Monitoring> getMonitoringListByDeviceNo(@Param("deviceNo") String deviceNo);


}
