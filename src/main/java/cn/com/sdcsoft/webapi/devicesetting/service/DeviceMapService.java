package cn.com.sdcsoft.webapi.devicesetting.service;

import cn.com.sdcsoft.webapi.devicesetting.entity.DeviceMap;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceMapService {

    @Autowired
    @Qualifier(value = "dtuSettingMongoTemplate")
    private MongoTemplate mongoTemplate;

    public boolean remove(String id) {
        Query query=new Query(Criteria.where(DeviceMap.FIELD_ID).is(id));
        DeleteResult result = mongoTemplate.remove(query,DeviceMap.class);
        return result.getDeletedCount()>0;
    }

    public void save(DeviceMap map){
        boolean f = remove(map.getId());
        mongoTemplate.save(map);
    }

    public List<DeviceMap> find(String deviceType,String deviceFactory,String deviceLine,String deviceAttr){
        Query query = new Query(Criteria.
                where(DeviceMap.FIELD_DEVICE_TYPE).is(deviceType).
                and(DeviceMap.FIELD_DEVICE_FACTORY).is(deviceFactory).
                and(DeviceMap.FIELD_DEVICE_LINE).is(deviceLine).
                and(DeviceMap.FIELD_DEVICE_ATTR).is(deviceAttr));
        return mongoTemplate.find(query,DeviceMap.class);
    }

    public List<DeviceMap> find(String deviceType,String deviceFactory,String deviceLine){
        Query query = new Query(Criteria.
                where(DeviceMap.FIELD_DEVICE_TYPE).is(deviceType).
                and(DeviceMap.FIELD_DEVICE_FACTORY).is(deviceFactory).
                and(DeviceMap.FIELD_DEVICE_LINE).is(deviceLine));
        return mongoTemplate.find(query,DeviceMap.class);
    }

    public DeviceMap get(String id){
        Query query = new Query(Criteria.where(DeviceMap.FIELD_ID).is(id));
        return mongoTemplate.findOne(query,DeviceMap.class);
    }
}
