package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.entity.DeviceStore;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_UserWechatDeviceStoreMapper;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/wechat/store/device")
public class Wechat_StoreController {

    @Autowired
    private Wechat_DB_UserWechatDeviceStoreMapper wechatDBDeviceMapper;

    @Autowired
    LAN_API lan_api;


    @GetMapping(value = "/list")
    public Result WxDevicelistbyemployeeMobile(String openId) {
        return Result.getSuccessResult(wechatDBDeviceMapper.getWxDeviceListByopenId(openId));
    }


    @PostMapping("/create")
    public Result editWxDevice(@RequestBody DeviceStore DeviceStore) {
        wechatDBDeviceMapper.insertWxDevice(DeviceStore);
        return Result.getSuccessResult();
    }

    @PostMapping("/modify")
    public Result modifyWxDevice(@RequestBody DeviceStore DeviceStore) {
        wechatDBDeviceMapper.updateStore(DeviceStore);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/remove")
    public Result deleteWxDevice(String openId, String deviceNo) {
        wechatDBDeviceMapper.deleteWxDevice(deviceNo, openId);
        return Result.getSuccessResult();
    }

    @PostMapping("/create/many")
    public Result insertManyStore(String storeList) {
        List<DeviceStore> list = JSON.parseArray(storeList, DeviceStore.class);
        for (int i = 0; i < list.size(); i++) {
            if (wechatDBDeviceMapper.getWxDeviceByopenIdAndDeviceNo(list.get(i).getOpenId(), list.get(i).getDeviceNo()) != null) {
                list.remove(i);
            }
        }
        if (list.size() > 0) {
            wechatDBDeviceMapper.insertManyStore(list);
            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }
}

