package cn.com.sdcsoft.webapi.web.weixinmanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DevicePermissionMap;
import cn.com.sdcsoft.webapi.wechat.mapper.Relation_DevicePermissionMapMapper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RestController
@RequestMapping(value = "/weixinmanage/devicePermission")
public class WeiWinManage_DevicePermissionMapController {

    @Autowired
    private Relation_DevicePermissionMapMapper rdpMapper;

    @Autowired
    LAN_API lan_api;

    /**
     * 查询设备列表-分页
     * @param rdc
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/relation_devicePermissionlistbyconditionandpage")
    public Result relation_devicePermissionlistbyconditionandpage(Relation_DevicePermissionMap rdc, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.getSuccessResult(new PageInfo(rdpMapper.getRelation_DevicePermissionMapListByCondition(rdc)));
    }
    /**
     * 编辑设备
     * @param rdc
     * @return
     */
    @PostMapping("/editrelation_devicePermission")
    public Result editrelation_devicePermission(@RequestBody Relation_DevicePermissionMap rdc){
        if(rdc.getId()!=null){
            rdpMapper.updateRelation_DevicePermissionMap(rdc);
        }else{
            JSONObject json = JSONObject.parseObject(lan_api.employeeFind(rdc.getEmployeeMobile()));
            JSONObject employee=(JSONObject)json.get("data");
            Timestamp d = new Timestamp(System.currentTimeMillis());
            rdc.setCreateDatetime(d);
            rdc.setOpenId(employee.getString("weiXin"));
            rdpMapper.insertRelation_DevicePermissionMap(rdc);
        }
        return Result.getSuccessResult();
    }

    /**
     * 删除设备
     * @param id
     * @return
     */
    @PostMapping(value = "/deleterelation_devicePermissionbyid")
    public Result deleterelation_devicePermissionbyid(@RequestParam int id){
        rdpMapper.deleteRelation_DevicePermissionMap(id);
        return Result.getSuccessResult();
    }

}

