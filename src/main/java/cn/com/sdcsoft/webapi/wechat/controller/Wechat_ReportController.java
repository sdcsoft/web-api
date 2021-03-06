package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.wechat.fegins.RPT_DATA_SERVICE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wechat/report")
public class Wechat_ReportController {

    @Autowired
    RPT_DATA_SERVICE rptapi;

    @GetMapping(value = "/device")
    public String getDeviceReportData(@RequestParam String deviceType, @RequestParam String begintime, @RequestParam String endtime, @RequestParam String deviceNo) {
        return rptapi.getDeviceReportDataForWechat(deviceType,begintime, endtime, deviceNo);
    }
}
