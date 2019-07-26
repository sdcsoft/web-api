package cn.com.sdcsoft.webapi.web.boilermanage.mapper;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.PartCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PartCategoryMapper {

    @Select("select * from Part_Category order by Sort asc")
    List<PartCategory> findAll();

    @Update("update Part_Category set Name=#{name},Sort=#{sort} where Id = #{id}")
    void modify(PartCategory partCategory);

    @Insert("insert into Part_Category (Name,Sort) values (#{name},#{sort})")
    void create(PartCategory partCategory);

    @Delete("delete from Part_Category where Id=#{id}")
    void remove(Integer id);

}
