package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_UserAddDeviceHistoryMapper;
import cn.com.sdcsoft.webapi.wechat.entity.UserAddDeviceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/webapi/wechat/userAddDeviceHistory")
public class Wechat_UserAddDeviceHistoryController {

    @Autowired
    private Wechat_DB_UserAddDeviceHistoryMapper wechat_db_User_addDeviceHistoryMapper;

    @Autowired
    LAN_API lan_api;

    @PostMapping("/create")
    public Result createUserAddDeviceHistory(@RequestBody UserAddDeviceHistory userAddDeviceHistory){
        wechat_db_User_addDeviceHistoryMapper.insertUserAddDeviceHistory(userAddDeviceHistory);
        return Result.getSuccessResult();
    }
}

