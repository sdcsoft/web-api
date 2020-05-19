package cn.com.sdcsoft.webapi.mapper.Enterprise_DB;

import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.Dictionary;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Enterprise_DB_DictionaryMapper {

    @Select("select * from Dictionary ")
    List<Dictionary> findAll();
}
