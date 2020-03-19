package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Resource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface Wechat_DB_ResourceMapper {

    @Select("select * from Resource")
    List<Resource> getResourceList();


}
