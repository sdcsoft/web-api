
package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_Relation_DeviceSmsMapMapper;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DeviceSmsMap;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@RestController
@RequestMapping(value = "/wechat/Relation_DeviceSmsMap")
public class Wechat_Relation_DeviceSmsMapController {

    @Autowired
    private Wechat_DB_Relation_DeviceSmsMapMapper wechat_db_relation_deviceSmsMapMapper;



    @GetMapping(value = "/find/deviceNo/employeeMobile")
    public Result getRelation_DeviceSmsMapByDeviceNoAndOpenId(String deviceNo,String employeeMobile ) {
        return Result.getSuccessResult(wechat_db_relation_deviceSmsMapMapper.getRelation_DeviceSmsMapByDeviceNoAndOpenId(deviceNo,employeeMobile));
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
            }

            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }

}

