package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DevicePermissionMap;
import cn.com.sdcsoft.webapi.wechat.entity.WxDevice;
import cn.com.sdcsoft.webapi.wechat.mapper.Relation_DevicePermissionMapMapper;
import cn.com.sdcsoft.webapi.wechat.mapper.WxDeviceMapper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.LinkedHashMap;


@RestController
@RequestMapping(value = "/webapi/wechat/WxDevice")
public class WxDeviceController {

    @Autowired
    private WxDeviceMapper wxDeviceMapper;

    @Autowired
    LAN_API lan_api;


    @GetMapping(value = "/list")
    public Result WxDevicelistbyemployeeMobile(String openId) {
        Result result = lan_api.employeeFindWechat(openId);
        LinkedHashMap data=(LinkedHashMap)result.getData();
        return Result.getSuccessResult(wxDeviceMapper.getWxDeviceListByemployeeMobile(data.get("mobile").toString()));
    }


    @GetMapping("/add")
    public Result editWxDevice(String openId,String deviceNo,String deviceType,String mqttName,int type){
        Result result = lan_api.employeeFindWechat(openId);
        LinkedHashMap data=(LinkedHashMap)result.getData();
        WxDevice wxDevice=new WxDevice();
        wxDevice.setEmployeeMobile(data.get("mobile").toString());
        wxDevice.setDeviceNo(deviceNo);
        wxDevice.setDeviceType(deviceType);
        wxDevice.setMqttName(mqttName);
        wxDevice.setType(type);
        wxDevice.setImgstyle(0);
        wxDeviceMapper.insertWxDevice(wxDevice);
        return Result.getSuccessResult();
    }


    @GetMapping(value = "/delete")
    public Result deleteWxDevice(String openId,String deviceNo){
        Result result = lan_api.employeeFindWechat(openId);
        LinkedHashMap data=(LinkedHashMap)result.getData();
        wxDeviceMapper.deleteWxDevice(deviceNo,data.get("mobile").toString());
        return Result.getSuccessResult();
    }
}

