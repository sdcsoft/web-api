package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.DictionaryValue;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DictionaryValueMapper {

    @Select("select Id,Label,Value,Sort,Type from Dictionary_Value where type = #{type} order by Sort asc")
    List<DictionaryValue> findByType(String type);
}
