package cn.com.sdcsoft.webapi.mapper.Wechat_DB;
import cn.com.sdcsoft.webapi.wechat.entity.WechatUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface Wechat_DB_WechatUserMapper {

    @Insert("insert into WechatUser(OpenId,RealName,CreateDatetime) values (#{openId},#{realName},#{createDatetime})")
    int insertWechatUser(WechatUser wechatUser);

    @Select("select * from WechatUser where OpenId=#{openId}")
    List<WechatUser> getWechatUserListByopenId(@Param("openId") String openId);
}
