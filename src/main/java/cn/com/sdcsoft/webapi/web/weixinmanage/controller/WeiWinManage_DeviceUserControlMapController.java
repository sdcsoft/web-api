package cn.com.sdcsoft.webapi.web.weixinmanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceUserControlMapMapper;
import cn.com.sdcsoft.webapi.web.weixinmanage.entity.DeviceUserControlMap;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RestController
@RequestMapping(value = "/webapi/weixinmanage/devicecontrol")
public class WeiWinManage_DeviceUserControlMapController {


    @Autowired
    private Wechat_DB_DeviceUserControlMapMapper rdcMapper;

    @Autowired
    LAN_API lan_api;


    @GetMapping(value = "/list/page")
    public Result getRelation_DeviceControlMapMapperListByConditionAndPage(DeviceUserControlMap rdc, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.getSuccessResult(new PageInfo(rdcMapper.getDeviceUserControlMapListByCondition(rdc)));
    }

    @PostMapping("/edit")
    public Result editRelation_DeviceControlMap(@RequestBody DeviceUserControlMap rdc) {
        if (rdc.getId() != null) {
            rdcMapper.updateDeviceUserControlMap(rdc);
        } else {
            JSONObject json = JSONObject.parseObject(lan_api.employeeFind(rdc.getEmployeeMobile()));
            JSONObject employee = (JSONObject) json.get("data");
            Timestamp d = new Timestamp(System.currentTimeMillis());
            rdc.setCreateDatetime(d);
            rdc.setOpenId(employee.getString("weiXin"));
            rdcMapper.insertDeviceUserControlMap(rdc);
        }
        return Result.getSuccessResult();
    }


    @PostMapping(value = "/delete")
    public Result deleteRelation_DeviceControlMapById(@RequestParam int id) {
        rdcMapper.deleteDeviceUserControlMap(id);
        return Result.getSuccessResult();
    }

}

