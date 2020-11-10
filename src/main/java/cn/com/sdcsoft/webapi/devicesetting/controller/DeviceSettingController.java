package cn.com.sdcsoft.webapi.devicesetting.controller;

import cn.com.sdcsoft.webapi.devicesetting.entity.DeviceSetting;
import cn.com.sdcsoft.webapi.devicesetting.service.DeviceSettingService;
import cn.com.sdcsoft.webapi.devicesetting.service.DeviceTypeService;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/device/setting")
public class DtuSettingController {

    @Autowired
    DeviceSettingService deviceSettingService;
    @Autowired
    DeviceTypeService deviceTypeService;


    @PostMapping("/create")
    public Result create(DeviceSetting setting){
        try{
            deviceSettingService.save(setting);
            return Result.getSuccessResult();
        }
        catch (Exception ex){
            return  Result.getFailResult(ex.getMessage());
        }
    }

    @RequestMapping("/types")
    public Result listTypes(){
        return Result.getSuccessResult(deviceTypeService.list());
    }


}
