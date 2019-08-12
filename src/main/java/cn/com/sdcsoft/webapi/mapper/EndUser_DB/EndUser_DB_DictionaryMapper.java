package cn.com.sdcsoft.webapi.mapper.EndUser_DB;

import cn.com.sdcsoft.webapi.web.endusermanage.entity.Dictionary;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EndUser_DB_DictionaryMapper {

    @Select("select * from Dictionary ")
    List<Dictionary> findAll();
}
