package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_OnlineRecordMapper;
import cn.com.sdcsoft.webapi.wechat.entity.OnlineRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/webapi/wechat/onlineRecord")
public class Wechat_OnlineRecordController {

    @Autowired
    private Wechat_DB_OnlineRecordMapper wechat_db_onlineRecordMapper;

    @Autowired
    LAN_API lan_api;

    @PostMapping("/create")
    public Result createOnlineRecord(@RequestBody OnlineRecord onlineRecord) {
        wechat_db_onlineRecordMapper.insertOnlineRecord(onlineRecord);
        return Result.getSuccessResult();
    }
}

