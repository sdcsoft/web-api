package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceStoreMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_StoreMapper;
import cn.com.sdcsoft.webapi.wechat.entity.DeviceStore;
import cn.com.sdcsoft.webapi.wechat.entity.Store;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/webapi/wechat/devicestore")
public class Wechat_DeviceStoreController {

    @Autowired
    private Wechat_DB_DeviceStoreMapper wechat_db_deviceStoreMapper;

    @Autowired
    LAN_API lan_api;


    @GetMapping(value = "/list")
    public Result WxDevicelistbyemployeeMobile(String openId) {
        return Result.getSuccessResult(wechat_db_deviceStoreMapper.getWxDeviceListByopenId(openId));
    }
    @GetMapping(value = "/check/openId")
    public Result checkOpenId(String openId) {
        List<DeviceStore> list= wechat_db_deviceStoreMapper.getWxDeviceListByopenId(openId);
        if(list.size()>0){
            return Result.getSuccessResult();
        }else{
            return Result.getFailResult("该用户更新缓存");
        }
    }

    @PostMapping("/create")
    public Result editWxDevice(@RequestBody DeviceStore deviceStore){
        wechat_db_deviceStoreMapper.insertWxDevice(deviceStore);
        return Result.getSuccessResult();
    }

    @PostMapping("/modify")
    public Result modifyWxDevice(@RequestBody DeviceStore deviceStore){
        wechat_db_deviceStoreMapper.updateDeviceStore(deviceStore);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/remove")
    public Result deleteWxDevice(String openId,String deviceNo){
        wechat_db_deviceStoreMapper.deleteWxDevice(deviceNo,openId);
        return Result.getSuccessResult();
    }

    @PostMapping("/create/many")
    public Result insertManyStore(String storeList){
        List<DeviceStore> list=JSON.parseArray(storeList,DeviceStore.class);
        for(int i=0;i<list.size();i++){
           if(wechat_db_deviceStoreMapper.getWxDeviceByopenIdAndDeviceNo(list.get(i).getOpenId(),list.get(i).getDeviceNo())!=null){
               list.remove(i);
           }
        }
        if(list.size()>0){
            wechat_db_deviceStoreMapper.insertManyStore(list);
            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }
}

