package cn.com.sdcsoft.webapi.devicesetting.controller;

import cn.com.sdcsoft.webapi.devicesetting.entity.DeviceMap;
import cn.com.sdcsoft.webapi.devicesetting.service.DeviceMapService;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devicesetting/map")
public class DeviceMapController {

    @Autowired
    DeviceMapService deviceMapService;

    @RequestMapping("/list")
    public Result listAttr(String deviceType,String deviceFactory,String deviceLine,String deviceAttr){
        if(StringUtils.isEmpty(deviceType)){
            return Result.getFailResult("设备类型不能为空！");
        }
        if(StringUtils.isEmpty(deviceFactory)){
            return Result.getFailResult("设备厂商不能为空！");
        }
        if(StringUtils.isEmpty(deviceLine)){
            return Result.getFailResult("设备型号不能为空！");
        }
        if(StringUtils.isEmpty(deviceAttr)){
            return Result.getSuccessResult(deviceMapService.find(deviceType,deviceFactory,deviceLine));
        }
        return Result.getSuccessResult(deviceMapService.find(deviceType,deviceFactory,deviceLine,deviceAttr));
    }

    @PostMapping(value = {"/create","/save"})
    public Result create(DeviceMap map){
        try{
            deviceMapService.save(map);
            return Result.getSuccessResult();
        }
        catch (Exception ex){
            return Result.getFailResult(ex.getMessage());
        }
    }
}
