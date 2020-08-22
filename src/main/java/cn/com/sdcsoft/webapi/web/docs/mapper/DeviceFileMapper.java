package cn.com.sdcsoft.webapi.web.docs.mapper;

import cn.com.sdcsoft.webapi.web.docs.entity.DeviceFile;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceFileMapper {

    @Select("select * from DeviceFile where DeviceNo=#{deviceNo}")
    List<DeviceFile> getDeviceDocs(@Param("deviceNo") String deviceNo);

    @Select("select * from DeviceFile  where DeviceNo is NULL")
    List<DeviceFile> getCommDocs();

}
