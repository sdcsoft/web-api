package cn.com.sdcsoft.webapi.web.weixinmanage.controller;


import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DevicePermissionMap;
import cn.com.sdcsoft.webapi.wechat.mapper.Relation_DevicePermissionMapMapper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/weixinmanage/employee")
public class WeiWinManage_EmployeeController {

    @Autowired
    LAN_API lan_api;

    @Autowired
    private Relation_DevicePermissionMapMapper relation_devicePermissionMapMapper;

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
