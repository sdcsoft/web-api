package cn.com.sdcsoft.webapi.dtusetting.service;

import cn.com.sdcsoft.webapi.dtusetting.entity.DeviceSetting;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class DeviceSettingService {

    @Autowired
    @Qualifier(value = "dtuSettingMongoTemplate")
    private MongoTemplate mongoTemplate;


    public boolean remove(String deviceNo) {
        Query query=new Query(Criteria.where(DeviceSetting.FIELD_DEVICE_NO).is(deviceNo));
        DeleteResult result = mongoTemplate.remove(query,DeviceSetting.class);
        return result.getDeletedCount()>0;
    }

    public void save(DeviceSetting setting){
        boolean f = remove(setting.getDeviceNo());
        mongoTemplate.save(setting);
    }

    public DeviceSetting get(String deviceNo){
        Query query = new Query(Criteria.where(DeviceSetting.FIELD_DEVICE_NO).is(deviceNo));
        return mongoTemplate.findOne(query,DeviceSetting.class);
    }
}
