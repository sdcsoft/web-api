package cn.com.sdcsoft.webapi.wechat.fegins;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RPT-DATA-SERVICE")
public interface RPT_DATA_SERVICE {
    @GetMapping("/getreportdata")
    String getDeviceReportDataForWechat(@RequestParam("deviceType") String deviceType,
                                        @RequestParam("begintime") String begintime,
                                        @RequestParam("endtime") String endtime,
                                        @RequestParam("deviceNo") String deviceNo);
}
