package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.Dictionary;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DictionaryMapper {

    @Select("select * from Dictionary ")
    List<Dictionary> findAll();
}
