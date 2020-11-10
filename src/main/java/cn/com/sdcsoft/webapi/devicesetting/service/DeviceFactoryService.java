package cn.com.sdcsoft.webapi.devicesetting.service;

import cn.com.sdcsoft.webapi.devicesetting.entity.DeviceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class DeviceFactoryService {
    @Autowired
    @Qualifier(value = "dtuSettingMongoTemplate")
    private MongoTemplate mongoTemplate;

    public List<DeviceFactory> list(){
        return mongoTemplate.findAll(DeviceFactory.class);
    }

    public List<DeviceFactory> list(String deviceType){
        Criteria criteria = Criteria.where(DeviceFactory.FIELD_DEVICE_TYPE).is(deviceType);
        Query query = new Query(criteria);
        return mongoTemplate.find(query,DeviceFactory.class);
    }


    public List<DeviceFactory> search(String deviceType,String name){
        Pattern patternName = Pattern.compile(String.format("^.*%s.*$", name), Pattern.CASE_INSENSITIVE);
        Criteria criteria = Criteria.where(DeviceFactory.FIELD_DEVICE_TYPE).is(deviceType).and(DeviceFactory.FIELD_FACTORY_NAME).regex(patternName);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, DeviceFactory.class);
    }
}
