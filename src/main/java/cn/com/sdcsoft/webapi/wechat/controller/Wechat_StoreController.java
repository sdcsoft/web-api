package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.entity.Store;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;


@RestController
@RequestMapping(value = "/webapi/wechat/store")
public class Wechat_StoreController {

    @Autowired
    private Wechat_DB_DeviceMapper wechatDBDeviceMapper;

    @Autowired
    LAN_API lan_api;


    @GetMapping(value = "/list")
    public Result WxDevicelistbyemployeeMobile(String openId) {
        return Result.getSuccessResult(wechatDBDeviceMapper.getWxDeviceListByopenId(openId));
    }


    @PostMapping("/add")
    public Result editWxDevice(@RequestBody Store store){
        wechatDBDeviceMapper.insertWxDevice(store);
        return Result.getSuccessResult();
    }

    @PostMapping("/modify")
    public Result modifyWxDevice(@RequestBody Store store){
        wechatDBDeviceMapper.updateStore(store);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/delete")
    public Result deleteWxDevice(String openId,String deviceNo){
        wechatDBDeviceMapper.deleteWxDevice(deviceNo,openId);
        return Result.getSuccessResult();
    }

}

