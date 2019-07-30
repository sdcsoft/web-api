package cn.com.sdcsoft.webapi.mapper.Customer_DB;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.Dictionary;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Customer_DB_DictionaryMapper {

    @Select("select * from Dictionary ")
    List<Dictionary> findAll();
}
