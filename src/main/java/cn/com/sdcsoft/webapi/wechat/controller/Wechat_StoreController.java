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
        Result result = lan_api.employeeFindWechat(openId);
        LinkedHashMap data=(LinkedHashMap)result.getData();
        return Result.getSuccessResult(wechatDBDeviceMapper.getWxDeviceListByemployeeMobile(data.get("mobile").toString()));
    }


    @GetMapping("/add")
    public Result editWxDevice(String openId,String deviceNo,String deviceType,String mqttName,int type){
        Result result = lan_api.employeeFindWechat(openId);
        LinkedHashMap data=(LinkedHashMap)result.getData();
        Store store =new Store();
        store.setEmployeeMobile(data.get("mobile").toString());
        store.setDeviceNo(deviceNo);
        store.setDeviceType(deviceType);
        store.setMqttName(mqttName);
        store.setType(type);
        store.setImgstyle(0);
        wechatDBDeviceMapper.insertWxDevice(store);
        return Result.getSuccessResult();
    }


    @GetMapping(value = "/delete")
    public Result deleteWxDevice(String openId,String deviceNo){
        Result result = lan_api.employeeFindWechat(openId);
        LinkedHashMap data=(LinkedHashMap)result.getData();
        wechatDBDeviceMapper.deleteWxDevice(deviceNo,data.get("mobile").toString());
        return Result.getSuccessResult();
    }
}

