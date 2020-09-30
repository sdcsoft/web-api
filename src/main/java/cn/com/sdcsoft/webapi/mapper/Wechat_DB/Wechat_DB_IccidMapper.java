package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Iccid;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


@Component
public interface Wechat_DB_IccidMapper {

    @Select("select * from Iccid")
    Iccid getIccid();


}
