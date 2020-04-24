package cn.com.sdcsoft.webapi.commcontroller.ScreenDisplay;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/screen/device")
public class DeviceController {


    @Autowired
    LAN_API lan_api;

    @RequestMapping("/snapshots")
    public Result getDeviceSnapshots(){
        return lan_api.deviceSnapshots();
    }


    @RequestMapping("/system/amount")
    public Result getSystemDevicesAmount(){
        return lan_api.countSystemDevicesAmount();
    }

    @RequestMapping("/enterprise/amount")
    public Result getEnterpriseDevicesAmount(Integer enterpriseId){
        return  lan_api.countEnterpriseDevicesAmount(enterpriseId);
    }

    @RequestMapping("/customer/amount")
    public Result getCustomerDevicesAmount(int customerId){
        return lan_api.countCustomerDevicesAmount(0,customerId);
    }

    @RequestMapping("/system/info")
    public Result countSystemDevicesInfo(){
        return lan_api.countSystemDevicesInfo();
    }

    @RequestMapping("/enterprise/info")
    public Result countEnterpriseDevicesInfo(Integer enterpriseId){
        return lan_api.countEnterpriseDevicesInfo(enterpriseId);
    }
}
