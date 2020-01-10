package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceStoreMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_ShowDeviceStoreMapper;
import cn.com.sdcsoft.webapi.wechat.entity.DeviceStore;
import cn.com.sdcsoft.webapi.wechat.entity.ShowDeviceStore;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/webapi/wechat/showDeviceStore")
public class Wechat_ShowDeviceStoreController {

    @Autowired
    private Wechat_DB_ShowDeviceStoreMapper wechat_db_showDeviceStoreMapper;

    @Autowired
    LAN_API lan_api;


    @GetMapping(value = "/list")
    public Result ShowDeviceStorelistbyemployeeMobile(String openId) {
        return Result.getSuccessResult(wechat_db_showDeviceStoreMapper.getShowDeviceStoreListByopenId(openId));
    }
    @GetMapping(value = "/check/openId")
    public Result checkOpenId(String openId) {
        List<ShowDeviceStore> list= wechat_db_showDeviceStoreMapper.getShowDeviceStoreListByopenId(openId);
        if(list.size()>0){
            return Result.getSuccessResult();
        }else{
            return Result.getFailResult("该用户更新缓存");
        }
    }

    @PostMapping("/create")
    public Result editShowDeviceStore(@RequestBody ShowDeviceStore showDeviceStore){
        wechat_db_showDeviceStoreMapper.insertShowDeviceStore(showDeviceStore);
        return Result.getSuccessResult();
    }

    @PostMapping("/modify")
    public Result modifyShowDeviceStore(@RequestBody ShowDeviceStore showDeviceStore){
        wechat_db_showDeviceStoreMapper.updateShowDeviceStore(showDeviceStore);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/remove")
    public Result deleteShowDeviceStore(String openId,String deviceNo){
        wechat_db_showDeviceStoreMapper.deleteShowDeviceStore(deviceNo,openId);
        return Result.getSuccessResult();
    }

    @PostMapping("/create/many")
    public Result insertManyStore(String storeList){
        List<ShowDeviceStore> list=JSON.parseArray(storeList,ShowDeviceStore.class);
        for(int i=0;i<list.size();i++){
           if(wechat_db_showDeviceStoreMapper.getShowDeviceStoreByopenIdAndDeviceNo(list.get(i).getOpenId(),list.get(i).getDeviceNo())!=null){
               list.remove(i);
           }
        }
        if(list.size()>0){
            wechat_db_showDeviceStoreMapper.insertManyShowDeviceStore(list);
            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }
}

