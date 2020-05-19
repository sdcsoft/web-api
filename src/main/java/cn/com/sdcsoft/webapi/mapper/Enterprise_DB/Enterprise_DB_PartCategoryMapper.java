package cn.com.sdcsoft.webapi.mapper.Enterprise_DB;

import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.PartCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Enterprise_DB_PartCategoryMapper {

    @Select("select * from Part_Category order by Sort asc")
    List<PartCategory> findAll();

    @Update("update Part_Category set Name=#{name},Sort=#{sort} where Id = #{id}")
    void modify(PartCategory partCategory);

    @Insert("insert into Part_Category (Name,Sort) values (#{name},#{sort})")
    void create(PartCategory partCategory);

    @Delete("delete from Part_Category where Id=#{id}")
    void remove(Integer id);

}
