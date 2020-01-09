package cn.com.sdcsoft.webapi.web.weixinmanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DevicePermissionMapMapper;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DevicePermissionMap;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RestController
@RequestMapping(value = "/webapi/weixinmanage/devicePermission")
public class WeiWinManage_DevicePermissionMapController {

    @Autowired
    private Wechat_DB_DevicePermissionMapMapper rdpMapper;

    @Autowired
    LAN_API lan_api;


    @GetMapping(value = "/list/page")
    public Result relation_devicePermissionListByConditionAndPage(Relation_DevicePermissionMap rdc, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.getSuccessResult(new PageInfo(rdpMapper.getRelation_DevicePermissionMapListByCondition(rdc)));
    }


    @PostMapping("/edit")
    public Result editRelation_devicePermission(@RequestBody Relation_DevicePermissionMap rdc) {
        if (rdc.getId() != null) {
            rdpMapper.updateRelation_DevicePermissionMap(rdc);
        } else {
            JSONObject json = JSONObject.parseObject(lan_api.employeeFind(rdc.getEmployeeMobile()));
            JSONObject employee = (JSONObject) json.get("data");
            Timestamp d = new Timestamp(System.currentTimeMillis());
            rdc.setCreateDatetime(d);
            rdc.setOpenId(employee.getString("weiXin"));
            rdpMapper.insertRelation_DevicePermissionMap(rdc);
        }
        return Result.getSuccessResult();
    }


    @PostMapping(value = "/delete")
    public Result deleteRelation_devicePermissionById(@RequestParam int id) {
        rdpMapper.deleteRelation_DevicePermissionMap(id);
        return Result.getSuccessResult();
    }

}

