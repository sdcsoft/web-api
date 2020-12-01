
package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_PrivatisationMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_Role_ResourceMapper;
import cn.com.sdcsoft.webapi.wechat.entity.Privatisation;
import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/wechat/privatisation")
public class Wechat_PrivatisationController {

    @Autowired
    private Wechat_DB_PrivatisationMapper wechat_db_privatisationMapper;
    @Autowired
    private Wechat_DB_Role_ResourceMapper wechat_db_role_resourceMapper;

    @GetMapping("/certification")
    public Result certification(String code,String deviceNo,String openId,String mobile,String userName){
        Privatisation p = wechat_db_privatisationMapper.getPrivatisationByDeviceNoAndOpenId(deviceNo,code);
        if(p==null){
            return Result.getFailResult("请输入正确的邀请码");
        }
        p.setOpenId(openId);
        p.setMobile(mobile);
        p.setRealName(userName);
        return Result.getSuccessResult(wechat_db_privatisationMapper.updatePrivatisation(p));
    }

    @GetMapping("/reset")
    public Result reset(Integer id){
        Privatisation p = wechat_db_privatisationMapper.getPrivatisationListById(id);
        p.setOpenId(UUID.randomUUID().toString().replace("-", ""));
        p.setMobile(null);
        p.setRealName(null);
        return Result.getSuccessResult(wechat_db_privatisationMapper.updatePrivatisation(p));
    }
    @GetMapping("/remove")
    public Result deletePrivatisationById(String id){
        return Result.getSuccessResult(wechat_db_privatisationMapper.deletePrivatisationById(id));
    }

    @GetMapping("/find/deviceNo/openId")
    public Result getPrivatisationByDeviceNoAndOpenId(String deviceNo,String openId){
        return Result.getSuccessResult(wechat_db_privatisationMapper.getPrivatisationListByDeviceNoAndOpenId(deviceNo,openId));
    }
    @GetMapping("/find/deviceNo")
    public Result findPrivatisationByDeviceNoAndOpenId(String deviceNo){
        return Result.getSuccessResult(wechat_db_privatisationMapper.findPrivatisationByDeviceNoAndOpenId(deviceNo));
    }

    @GetMapping("/find/buyersOpenId")
    public Result findPrivatisationBybuyersOpenId(String buyersOpenId){
        return Result.getSuccessResult(wechat_db_privatisationMapper.getPrivatisationListBybuyersOpenId(buyersOpenId));
    }
    @PostMapping("/create/many")
    public Result insertManyPrivatisation(String plist){
        List<Privatisation> list =JSON.parseArray(plist,Privatisation.class);
        Privatisation pr = list.get(0);
        List<Privatisation> result=wechat_db_privatisationMapper.getPrivatisationListBybuyersOpenId(pr.getBuyersOpenId());

        if(result.size()>0){
            Calendar calendar = Calendar.getInstance();

            for(int i = 0;i<result.size();i++){
                Privatisation p = result.get(i);
                calendar.setTime(p.getDueTime());
                calendar.add(Calendar.MONTH,12);
                p.setDueTime( new Timestamp(calendar.getTime().getTime()));
                wechat_db_privatisationMapper.updatePrivatisationDueTime(p);
                Role_Resource r= new Role_Resource();
                r.setDueTime(new Timestamp(calendar.getTime().getTime()));
                r.setDeviceNo(p.getDeviceNo());
                r.setOpenId(p.getOpenId());
                wechat_db_role_resourceMapper.updateRole_ResourceDueTime(r);
            }

        }else {
            pr.setOpenId(pr.getBuyersOpenId());
            list.set(0, pr);
            if(list.size()>1){
                for(int i = 1;i<list.size();i++){
                    Privatisation p = list.get(i);
                    p.setOpenId(UUID.randomUUID().toString().replace("-", ""));
                    list.set(i, p);
                }
            }
            wechat_db_privatisationMapper.insertManyPrivatisation(list);
        }

            return Result.getSuccessResult();
    }
}

