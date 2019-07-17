package cn.com.sdcsoft.webapi.wechat.controller;


import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DevicePermissionMap;
import cn.com.sdcsoft.webapi.wechat.mapper.Relation_DevicePermissionMapMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
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
    private Relation_DevicePermissionMapMapper relation_devicePermissionMapMapper;

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
    //微信管理后台登录
    @PostMapping(value = "/login", produces = { "application/json;charset=UTF-8" })
    public Result login(String account,String passWord) {
        JSONObject json = JSONObject.parseObject(lan_api.employeeFind(account));
        JSONObject employee=(JSONObject)json.get("data");
        String password=employee.get("password").toString();
        if (null == employee)
            return Result.getFailResult(0,"用户名或者密码输入错误");
        if (employee.getString("password").equals(passWord)) {
            if (Employee.STATUS_ENABLE == employee.getIntValue("status")) {
                return Result.getFailResult(1,"success");
            } else {
                return Result.getFailResult(0,"您的用户账号被禁用，请联系系统管理人员！");
            }
        } else {
            return Result.getFailResult(0,"用户名或者密码输入错误");
        }
    }
}
