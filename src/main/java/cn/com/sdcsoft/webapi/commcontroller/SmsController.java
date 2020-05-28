package cn.com.sdcsoft.webapi.commcontroller;


import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.utils.DeviceSmsCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


//@RestController
//@RequestMapping(value = "/cache/sms/device/exception")
//public class SmsController {
//
//    @Autowired
//    DeviceSmsCacheUtil deviceSmsCacheUtil;
//
//    @GetMapping("/get")
//    public Result getDeviceSmsCache(String deviceNo,String exceptionName){
//        String key = String.format("%s-%s",deviceNo,exceptionName);
//        Object item = deviceSmsCacheUtil.getCacheItem(key);
//        if(null == item){
//            return Result.getSuccessResult();
//        }
//        return Result.getSuccessResult(item);
//    }
//
//    @PostMapping("/update")
//    public Result createDeviceSmsCache(String deviceNo,String exceptionName){
//        String key = String.format("%s-%s",deviceNo,exceptionName);
//        deviceSmsCacheUtil.putCacheItem(key,new Date().getTime());
//        return Result.getSuccessResult();
//    }
//
//
//}
