package cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper;

import cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity.Monitoring;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface MonitoringMapper {
    @Select("Select * from  Monitoring where Status =0")
    List<Monitoring> find();
}
