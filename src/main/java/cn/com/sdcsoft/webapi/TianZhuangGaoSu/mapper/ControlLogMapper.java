package cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper;

import cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity.ControlLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface ControlLogMapper {
    @Select("Select * from  ControlLog ")
    List<ControlLog> find();

    @Insert("insert into ControlLog(Command,OpenId,CreateDatetime) values (#{command},#{openId},#{createDatetime})")
    int insert(ControlLog controlLog);
}
