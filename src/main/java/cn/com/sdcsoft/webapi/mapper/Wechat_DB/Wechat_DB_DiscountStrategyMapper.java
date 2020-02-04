package cn.com.sdcsoft.webapi.mapper.Wechat_DB;

import cn.com.sdcsoft.webapi.wechat.entity.DiscountStrategy;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public interface Wechat_DB_DiscountStrategyMapper {

    @Select("select * from DiscountStrategy")
    List<DiscountStrategy> getAll();

    @Select("select * from DiscountStrategy where #{now} between StartDatetime and EndDatetime order by BaseNumber asc")
    List<DiscountStrategy> getDiscountStrategies(Date now);

    @Insert("insert into DiscountStrategy(StrategyName,BaseNumber,Discount,StrategyType,StartDatetime,EndDatetime) values (#{strategyName},#{baseNumber},#{discount},#{strategyType},#{startDatetime},#{endDatetime})")
    int insertDiscountStrategy(DiscountStrategy discountStrategy);

    @Update("update DiscountStrategy set StrategyName=#{strategyName},BaseNumber=#{baseNumber},Discount=#{discount},StrategyType=#{strategyType},StartDatetime=#{startDatetime},EndDatetime=#{endDatetime} where Id = #{id}")
    int updateDiscountStrategy(DiscountStrategy discountStrategy);



}
