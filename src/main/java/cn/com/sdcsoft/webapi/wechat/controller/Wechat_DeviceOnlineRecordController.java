package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceOnlineRecordMapper;
import cn.com.sdcsoft.webapi.wechat.entity.DeviceOnlineRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/wechat/DeviceOnlineRecord")
public class Wechat_DeviceOnlineRecordController {

    @Autowired
    private Wechat_DB_DeviceOnlineRecordMapper wechat_db_deviceOnlineRecordMapper;

    @Autowired
    LAN_API lan_api;

    @PostMapping("/create")
    public Result createOnlineRecord(@RequestBody DeviceOnlineRecord deviceOnlineRecord) {
        wechat_db_deviceOnlineRecordMapper.insertDeviceOnlineRecord(deviceOnlineRecord);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/find/deviceNo/openId")
    public Result getDeviceOnlineRecordByDeviceNoAndOpenId(String deviceNo,String openId ) {
        return Result.getSuccessResult(wechat_db_deviceOnlineRecordMapper.getDeviceOnlineRecordListByDeviceNoAndOpenId(deviceNo,openId));
    }
}

