package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.PartCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PartCategoryMapper {

    @Select("select * from Part_Category order by Sort asc")
    List<PartCategory> findAll();
}
