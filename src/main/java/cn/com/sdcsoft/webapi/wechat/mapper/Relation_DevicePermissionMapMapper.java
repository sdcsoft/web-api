package cn.com.sdcsoft.webapi.wechat.mapper;

import cn.com.sdcsoft.webapi.wechat.entity.Relation_DeviceControlMap;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DevicePermissionMap;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Relation_DevicePermissionMapMapper {

    @Select("select * from Relation_DevicePermissionMap where OpenId=#{OpenId}")
    List<Relation_DevicePermissionMap> getRelation_DevicePermissionMapListByOpenId(@Param("OpenId") String OpenId);

}
