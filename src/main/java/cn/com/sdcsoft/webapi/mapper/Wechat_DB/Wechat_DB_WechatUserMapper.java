package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.WechatUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface Wechat_DB_WechatUserMapper {

    @Insert("insert into WechatUser(OpenId,RealName,CreateDatetime) values (#{openId},#{realName},#{createDatetime})")
    int insertWechatUser(WechatUser wechatUser);

    @Select("select * from WechatUser where OpenId=#{openId}")
    List<WechatUser> getWechatUserListByopenId(@Param("openId") String openId);

    @Select("select * from WechatUser where OpenId=#{openId}")
   WechatUser getWechatUserByopenId(@Param("openId") String openId);


    @Select("select * from WechatUser where Mobile=#{moblie}")
    WechatUser getWechatUserListByMobile(@Param("mobile") String moblie);

    @Update("update WechatUser set mobile=#{mobile} where OpenId = #{openId}")
    int updateWechatUser(@Param("mobile") String moblie,@Param("openId") String openId);
}
