package cn.com.sdcsoft.webapi.wechat.controller;


import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DevicePermissionMapMapper;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DevicePermissionMap;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/wechat/employee")
public class Wechat_EmployeeController {

    @Autowired
    LAN_API lan_api;

    @Autowired
    private Wechat_DB_DevicePermissionMapMapper relation_devicePermissionMapMapper;

    @GetMapping(value = "/getwx")
    public Result getWx(String openid) {
        Result result = lan_api.employeeFindWechat(openid);
        if (result.getCode() > 0){//微信未绑定或其他错误
            return result;
        }
        // TODO 检索微信自己的库，进行相关处理
        // TODO ......
        return result;
    }
    @GetMapping(value = "/getSoldPermissions")
    public int getSoldPermissions(String openid) {
        List<Relation_DevicePermissionMap> list=relation_devicePermissionMapMapper.getRelation_DevicePermissionMapListByOpenId(openid);
        if(list.size()>0){
            return Result.RESULT_CODE_SUCCESS;
        }else{
            return Result.RESULT_CODE_FAIL;
        }
    }
    @GetMapping(value = "/find", produces = { "application/json;charset=UTF-8" })
    public String find(String loginId) {
        return lan_api.employeeFind(loginId);
    }

}
