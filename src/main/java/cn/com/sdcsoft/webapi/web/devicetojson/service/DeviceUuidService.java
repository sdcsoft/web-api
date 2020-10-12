package cn.com.sdcsoft.webapi.web.devicetojson.service;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.utils.DeviceUuidCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceUuidService {
    @Autowired
    DeviceUuidCacheUtil cacheUtil;

    @Autowired
    LAN_API lan_api;

    public String getDeviceNo(String uuid) {
        Object cacheItem = cacheUtil.getCacheItem(uuid);
        if(null == cacheItem){
            Result result = lan_api.deviceUuid(uuid);
            if (Result.RESULT_CODE_SUCCESS == result.getCode()) {
                String deviceNo = result.getData().toString();
                cacheUtil.putCacheItem(uuid,deviceNo);
                return deviceNo;
            }
            return null;
        }

        return cacheItem.toString();
    }

}
