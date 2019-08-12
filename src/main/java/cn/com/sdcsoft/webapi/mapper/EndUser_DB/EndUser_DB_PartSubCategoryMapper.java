package cn.com.sdcsoft.webapi.mapper.EndUser_DB;

import cn.com.sdcsoft.webapi.web.endusermanage.entity.PartSubCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EndUser_DB_PartSubCategoryMapper {
    @Select("select * from Part_SubCategory where CategoryId=#{partCategoryId} order by Sort asc")
    List<PartSubCategory> findAllPartCategoryId(int partCategoryId);

    @Update("update Part_SubCategory set CategoryId=#{categoryId},Name=#{name},Sort=#{sort} where Id = #{id}")
    void modify(PartSubCategory partSubCategory);

    @Insert("insert into Part_SubCategory (CategoryId,Name,Sort) values (#{categoryId},#{name},#{sort})")
    void create(PartSubCategory partSubCategory);

    @Delete("delete from Part_SubCategory where Id=#{id}")
    void remove(Integer id);
}
