package cn.com.sdcsoft.webapi.devicesetting.service;

import cn.com.sdcsoft.webapi.devicesetting.entity.DeviceLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceLineService {

    @Autowired
    @Qualifier(value = "dtuSettingMongoTemplate")
    private MongoTemplate mongoTemplate;

    public List<DeviceLine> list(String factoryName){
        Query query = new Query(Criteria.where(DeviceLine.FIELD_DEVICE_FACTORY).is(factoryName));
        return mongoTemplate.find(query,DeviceLine.class);
    }

}
