package cn.com.sdcsoft.webapi.dtusetting.service;


import cn.com.sdcsoft.webapi.dtusetting.entity.DeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypeService {

    @Autowired
    @Qualifier(value = "dtuSettingMongoTemplate")
    private MongoTemplate mongoTemplate;

    public List<DeviceType> list(){
        return mongoTemplate.findAll(DeviceType.class);
    }
}
