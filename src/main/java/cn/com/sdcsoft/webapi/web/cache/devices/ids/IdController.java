package cn.com.sdcsoft.webapi.web.cache.devices.ids;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.web.cache.devices.ids.service.DeviceIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi/cache/devices/ids")
public class IdController {
    @Autowired
    DeviceIdService service;

    @RequestMapping("/get")
    public Result getIdList(){
        return Result.getSuccessResult("ok",service.getDeviceIdSet());
    }

    @RequestMapping("/check")
    public Result checkSystemId(String id){
        if(service.isSystemDevice(id))
            return Result.getSuccessResult();
        return Result.getFailResult("") ;
    }
}
