package cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper;

import cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity.Device;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface DeviceMapper {
    @Select("Select * from Device")
    List<Device> find();

    @Select("Select * from Device  where Type=#{type}")
    List<Device> findByType(@Param("type") String type);

    @Insert("insert into Device(DeviceNo,DeviceName,PointIndexMap,DeviceDataMap) values (#{deviceNo},#{deviceName},#{pointIndexMap},#{deviceDataMap})")
    int insert(Device device);


}
