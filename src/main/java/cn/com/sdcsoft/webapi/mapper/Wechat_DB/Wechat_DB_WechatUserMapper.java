package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.Store;
import cn.com.sdcsoft.webapi.wechat.entity.WechatUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


@Component
public interface Wechat_DB_WechatUserMapper {

    @Insert("insert into WechatUser(OpenId,RealName,CreateDatetime) values (#{openId},#{realName},#{createDatetime})")
    int insertWechatUser(WechatUser wechatUser);


}
