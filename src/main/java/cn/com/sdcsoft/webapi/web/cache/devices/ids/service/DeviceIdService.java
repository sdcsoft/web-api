package cn.com.sdcsoft.webapi.web.cache.devices.ids.service;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DeviceIdService {

    @Autowired
    LAN_API api;

    private static Set<String> systemDeviceIds;


    public void load() {
        try {
            Result result = api.deviceIdList();
            if (Result.RESULT_CODE_SUCCESS == result.getCode()) {
                List<String> list = (List<String>) result.getData();
                if(null != list && list.size()>0){
                    systemDeviceIds = new HashSet<>(list);
                }
                else{
                    systemDeviceIds = Collections.emptySet();
                }
            }
        } catch (Exception e) {
            systemDeviceIds = Collections.emptySet();
            e.printStackTrace();
        }
    }

    public Set<String> getDeviceIdSet() {
        return systemDeviceIds;
    }

    public boolean isSystemDevice(String deviceNo) {
        try {
            if (systemDeviceIds.contains(deviceNo)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

}
