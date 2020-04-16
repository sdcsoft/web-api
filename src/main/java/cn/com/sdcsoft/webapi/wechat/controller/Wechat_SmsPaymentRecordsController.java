package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_SmsPaymentRecordsMapper;
import cn.com.sdcsoft.webapi.wechat.entity.SmsPaymentRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;


@RestController
@RequestMapping(value = "/webapi/wechat/smsPaymentRecords")
public class Wechat_SmsPaymentRecordsController {

    @Autowired
    private Wechat_DB_SmsPaymentRecordsMapper wechat_db_smsPaymentRecordsMapper;



    @GetMapping(value = "/list/openId/deviceNo")
    public Result getSmsPaymentRecordsListByDeviceNoAndOpenId(String deviceNo,String openId) {
        return Result.getSuccessResult(wechat_db_smsPaymentRecordsMapper.getSmsPaymentRecordsListByDeviceNoAndOpenId(deviceNo,openId));
    }
    @GetMapping(value = "/list/deviceNo")
    public Result getSmsPaymentRecordsListByDeviceNo(String deviceNo) {
        return Result.getSuccessResult(wechat_db_smsPaymentRecordsMapper.getSmsPaymentRecordsListByDeviceNo(deviceNo));
    }


    @PostMapping(value = "/list")
    public Result getSmsPaymentRecordsListByCondition(@RequestBody SmsPaymentRecords smsPaymentRecords) {
        return Result.getSuccessResult(wechat_db_smsPaymentRecordsMapper.getSmsPaymentRecordsListByCondition(smsPaymentRecords));
    }

    @PostMapping("/create")
    public Result editSmsPaymentRecords(@RequestBody SmsPaymentRecords smsPaymentRecords) {

        SmsPaymentRecords smsPaymentRecords1 =wechat_db_smsPaymentRecordsMapper.getSmsPaymentRecordsListByDeviceNoAndIMEI(smsPaymentRecords.getDeviceNo(),smsPaymentRecords.getiMEI());
        Calendar calendar = Calendar.getInstance();
        if(smsPaymentRecords1==null){
            calendar.add(Calendar.YEAR,1);
            smsPaymentRecords.setDueTime(new Timestamp(calendar.getTime().getTime()));
            smsPaymentRecords.setStatus(0);
            return Result.getSuccessResult(wechat_db_smsPaymentRecordsMapper.insertSmsPaymentRecords(smsPaymentRecords));
        }else{
            Timestamp startTime=smsPaymentRecords1.getDueTime();
            calendar.setTime(startTime);
            smsPaymentRecords1.setDueTime(new Timestamp(calendar.getTime().getTime()));
            smsPaymentRecords1.setStatus(0);
            return Result.getSuccessResult(wechat_db_smsPaymentRecordsMapper.updateSmsPaymentRecords(smsPaymentRecords1));
        }

    }
    @PostMapping("/modify")
    public Result editRelation_devicePermission(@RequestBody SmsPaymentRecords smsPaymentRecords) {
        int i=wechat_db_smsPaymentRecordsMapper.updateSmsPaymentRecords(smsPaymentRecords);
        return Result.getSuccessResult();
    }
}

