package cn.com.sdcsoft.webapi.mapper.EndUser_DB;

import cn.com.sdcsoft.webapi.web.endusermanage.entity.DictionaryValue;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EndUser_DB_DictionaryValueMapper {

    @Select("select Id,Label,Value,Sort,Type from Dictionary_Value where type = #{type} order by Sort asc")
    List<DictionaryValue> findByType(String type);
}
