package cn.com.sdcsoft.webapi.devicesetting.service;

import cn.com.sdcsoft.webapi.devicesetting.entity.DeviceAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceAttrService {

    @Autowired
    @Qualifier(value = "dtuSettingMongoTemplate")
    private MongoTemplate mongoTemplate;

    public List<DeviceAttr> list(String lineName){
        Query query = new Query(Criteria.where(DeviceAttr.FIELD_DEVICE_LINE).is(lineName));
        return mongoTemplate.find(query,DeviceAttr.class);
    }

}
