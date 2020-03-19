
package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_ResourceMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_Role_ResourceMapper;
import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@RestController
@RequestMapping(value = "/webapi/wechat/RoleResource")
public class Wechat_Role_ResourceController {

    @Autowired
    private Wechat_DB_Role_ResourceMapper wechat_db_role_resourceMapper;


    @GetMapping(value = "/list")
    public Result Role_ResourcelistbyemployeeMobile(String openId) {
        return Result.getSuccessResult(wechat_db_role_resourceMapper.getRole_ResourceListByopenId(openId));
    }

    @GetMapping(value = "/list/deviceNo")
    public Result Role_ResourcelistbyDeviceNo(String deviceNo) {
        return Result.getSuccessResult(wechat_db_role_resourceMapper.getRole_ResourceListByDeviceNo(deviceNo));
    }

    @PostMapping("/create")
    public Result editRole_Resource(@RequestBody Role_Resource role_resource) {
        wechat_db_role_resourceMapper.insertRole_Resource(role_resource);
        return Result.getSuccessResult();
    }

    @PostMapping("/modify")
    public Result modifyRole_Resource(@RequestBody Role_Resource role_resource) {
        wechat_db_role_resourceMapper.updateRole_Resource(role_resource);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/remove")
    public Result deleteRole_Resource(String openId, String deviceNo) {
        wechat_db_role_resourceMapper.deleteWxDevice(deviceNo, openId);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/find/deviceNo/openId")
    public Result getRole_ResourceListByDeviceNoAndOpenId(String deviceNo,String openId ) {
        return Result.getSuccessResult(wechat_db_role_resourceMapper.getRole_ResourceListByDeviceNoAndOpenId(deviceNo,openId));
    }


    @GetMapping(value = "/wechat/remove")
    public Result deleteRole_ResourceBywx(String id) {
        wechat_db_role_resourceMapper.deleteWxDeviceById(id);
        return Result.getSuccessResult();
    }
    @PostMapping("/create/many")
    public Result insertManyRole_Resource(String role_ResourceList){
        List<Role_Resource> list=JSON.parseArray(role_ResourceList,Role_Resource.class);
        List<Role_Resource> listDb =new ArrayList<>(); ;
        if(list.size()>0){
            for( int i=0 ;i < list.size();i++){
                Role_Resource role_resource= wechat_db_role_resourceMapper.getRole_ResourceListByitem(list.get(i).getDeviceNo(),list.get(i).getOpenId(),list.get(i).getResId().toString());
                Timestamp startTime=new Timestamp(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();

                if(null!=role_resource){
                     startTime=role_resource.getDueTime();
                     calendar.setTime(startTime);
                    if(list.get(i).getRangeType()==1){
                        //天
                        calendar.add(Calendar.DAY_OF_YEAR,list.get(i).getRange()*list.get(i).getAmount());
                    }
                    if(list.get(i).getRangeType()==2){
                        //月
                        calendar.add(Calendar.MONTH,list.get(i).getRange()*list.get(i).getAmount());
                    }
                    role_resource.setDueTime(new Timestamp(calendar.getTime().getTime()));
                    wechat_db_role_resourceMapper.updateRole_ResourceById(role_resource);

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
                    listDb.add( list.get(i));
                }

            if(listDb.size()>0){
                wechat_db_role_resourceMapper.insertManyRole_Resource(listDb);
            }

            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }
    @GetMapping ("/date")
    public Result date(String role_ResourceList){
        Role_Resource role_resource= wechat_db_role_resourceMapper.getRole_ResourceListByitem("0203800002","oGRug4ljfu_8pZ25szc","2");
        Timestamp startTime=new Timestamp(System.currentTimeMillis());
        if(null!=role_resource){
            startTime=role_resource.getDueTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        //calendar.add(Calendar.MONTH, 12);

        return Result.getSuccessResult(calendar.getTime());
    }
}

