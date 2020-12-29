
package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_MonitoringMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/wechat/monitoring")
public class Wechat_MonitoringController {

    @Autowired
    private Wechat_DB_MonitoringMapper wechat_db_monitoringMapper;



    @GetMapping("/find/deviceNo")
    public Result getMonitoringListByDeviceNo(String deviceNo){
        return Result.getSuccessResult(wechat_db_monitoringMapper.getMonitoringListByDeviceNo(deviceNo));
    }


}

