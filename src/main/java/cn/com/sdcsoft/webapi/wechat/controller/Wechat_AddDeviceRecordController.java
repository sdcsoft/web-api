package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_AddDeviceRecordMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_OnlineRecordMapper;
import cn.com.sdcsoft.webapi.wechat.entity.AddDeviceRecord;
import cn.com.sdcsoft.webapi.wechat.entity.OnlineRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/webapi/wechat/addDeviceRecord")
public class Wechat_AddDeviceRecordController {

    @Autowired
    private Wechat_DB_AddDeviceRecordMapper wechat_db_addDeviceRecordMapper;

    @Autowired
    LAN_API lan_api;

    @PostMapping("/create")
    public Result createAddDeviceRecord(@RequestBody AddDeviceRecord addDeviceRecord){
        wechat_db_addDeviceRecordMapper.insertAddDeviceRecord(addDeviceRecord);
        return Result.getSuccessResult();
    }
}

