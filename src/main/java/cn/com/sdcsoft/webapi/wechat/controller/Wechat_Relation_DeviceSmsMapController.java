
package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_Relation_DeviceSmsMapMapper;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DeviceSmsMap;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@RestController
@Configuration
@EnableScheduling
@RequestMapping(value = "/wechat/Relation_DeviceSmsMap")
public class Wechat_Relation_DeviceSmsMapController {

    @Autowired
    private Wechat_DB_Relation_DeviceSmsMapMapper wechat_db_relation_deviceSmsMapMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping(value = "/find/deviceNo/employeeMobile")
    public Result getRelation_DeviceSmsMapByDeviceNoAndEmployeeMobile(String deviceNo,String employeeMobile ) {
        return Result.getSuccessResult(wechat_db_relation_deviceSmsMapMapper.getRelation_DeviceSmsMapByDeviceNoAndEmployeeMobile(deviceNo,employeeMobile));
    }

    @GetMapping(value = "/find/deviceNo/openId")
    public Result getRelation_DeviceSmsMapByDeviceNoAndOpenId(String deviceNo,String openId ) {
        return Result.getSuccessResult(wechat_db_relation_deviceSmsMapMapper.getRelation_DeviceSmsMapByDeviceNoAndOpenId(deviceNo,openId));
    }

    @PostMapping("/modify")
    public Result modifyRole_Resource(@RequestBody Relation_DeviceSmsMap relation_deviceSmsMap) {
        wechat_db_relation_deviceSmsMapMapper.updateRelation_DeviceSmsMapById(relation_deviceSmsMap);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/remove")
    public Result deleteRelation_DeviceSmsMap(String openId, String deviceNo) {
        if(wechat_db_relation_deviceSmsMapMapper.deleteRelation_DeviceSmsMapByDeviceNoAndOpenId(deviceNo, openId)>0){
            return Result.getSuccessResult();
        }
        return Result.getFailResult("删除失败");
    }

    @PostMapping("/create/many")
    public Result insertManyRelation_DeviceSmsMap(String deviceSmsMapList){
        List<Relation_DeviceSmsMap> list=JSON.parseArray(deviceSmsMapList,Relation_DeviceSmsMap.class);
        List<Relation_DeviceSmsMap> listDb =new ArrayList<>(); ;
        if(list.size()>0){
            for( int i=0 ;i < list.size();i++){
                Relation_DeviceSmsMap relation_deviceSmsMap= wechat_db_relation_deviceSmsMapMapper.getRelation_DeviceSmsMapOneByDeviceNoAndOpenId(list.get(i).getDeviceNo(),list.get(i).getEmployeeMobile());
                Timestamp startTime=new Timestamp(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();

                if(null!=relation_deviceSmsMap){
                    startTime=relation_deviceSmsMap.getDueTime();
                    calendar.setTime(startTime);
                    if(list.get(i).getRangeType()==1){
                        //天
                        calendar.add(Calendar.DAY_OF_YEAR,list.get(i).getRange()*list.get(i).getAmount());
                    }
                    if(list.get(i).getRangeType()==2){
                        //月
                        calendar.add(Calendar.MONTH,list.get(i).getRange()*list.get(i).getAmount());
                    }
                    relation_deviceSmsMap.setDueTime(new Timestamp(calendar.getTime().getTime()));
                    wechat_db_relation_deviceSmsMapMapper.updateRelation_DeviceSmsMap(relation_deviceSmsMap);

                    continue;
                }

                calendar.setTime(startTime);
                if(list.get(i).getRangeType()==1){
                    //天
                    calendar.add(Calendar.DAY_OF_YEAR,list.get(i).getRange()*list.get(i).getAmount());
                }
                if(list.get(i).getRangeType()==2){
                    //月
                    calendar.add(Calendar.MONTH,list.get(i).getRange()*list.get(i).getAmount());
                }

                list.get(i).setDueTime(new Timestamp(calendar.getTime().getTime()));
                list.get(i).setCreateDatetime(new Timestamp(System.currentTimeMillis()));
                listDb.add( list.get(i));
            }

            if(listDb.size()>0){
                wechat_db_relation_deviceSmsMapMapper.insertManyRelation_DeviceSmsMap(listDb);
                for(int i=0;i<listDb.size();i++){
                    rabbitTemplate.convertAndSend("WechatDeviceExMapMobileExchange","","ADD-"+listDb.get(i).getDeviceNo()+"-"+listDb.get(i).getEmployeeMobile());
                }
            }
            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }
    //每天凌晨一点检索到期服务删除并发送队列消息
    //*/5 * * * * ?
    @Scheduled(cron="0 0 1 * * ?")
    public void check() throws IOException {
        Timestamp nowTime=new Timestamp(System.currentTimeMillis());
        if(wechat_db_relation_deviceSmsMapMapper.deleteRelation_DeviceSmsMapByTime(nowTime.toString())>0){
            rabbitTemplate.convertAndSend("WechatDeviceExMapMobileExchange","","Reload");
        }
    }

}

