package cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper;

import cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;


@Component
public interface UserMapper {
    @Select("Select * from  User where OpenId = #{openId}")
    User findUserByOpenId(@Param("openId") String openId);

    @Select("Select * from  User where UnionId = #{unionId}")
    User findUserByUnionId(@Param("unionId") String unionId);

    @Insert("insert into User(Mobile,UserName,OpenId,UnionId,RoleId) values (#{mobile},#{userName},#{openId},#{unionId},0)")
    int insert(User user);
    @Insert("insert into User (OpenId) values (#{invCode})")
    void createInvCode(String invCode);

    @Update("update User set Mobile=#{mobile},UserName=#{userName},UnionId=#{unionId},UserName=#{userName},OpenId=#{openId} where Id = #{id}")
    int updateUser(User user);
}
