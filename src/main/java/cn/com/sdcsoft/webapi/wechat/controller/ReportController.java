package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.wechat.fegins.RPT_DATA_SERVICE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi/wechat/report")
public class ReportController {

    @Autowired
    RPT_DATA_SERVICE rptapi;

    @GetMapping("/device")
    public String getDeviceReportData(@RequestParam String deviceType, @RequestParam String begintime, @RequestParam String endtime, @RequestParam String deviceNo) {
        return rptapi.getDeviceReportDataForWechat(deviceType,begintime, endtime, deviceNo);
    }
}
