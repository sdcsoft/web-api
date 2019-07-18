package cn.com.sdcsoft.webapi.web.weixinmanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DeviceControlMap;
import cn.com.sdcsoft.webapi.wechat.mapper.Relation_DeviceControlMapMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.LinkedHashMap;


@RestController
@RequestMapping(value = "/weixinmanage/devicecontrol")
public class WeiWinManage_DeviceControlMapController {


    @Autowired
    private Relation_DeviceControlMapMapper rdcMapper;

    @Autowired
    LAN_API lan_api;

    @GetMapping(value = "/getdevicecontrolList")
    public Result getdevicecontrolList(String openid) {
        Result result = lan_api.employeeFindWechat(openid);
        LinkedHashMap data=(LinkedHashMap)result.getData();
        Relation_DeviceControlMap rdc =new Relation_DeviceControlMap();

        return Result.getSuccessResult(rdcMapper.getRelation_DeviceControlMapListByCondition(rdc));
    }


    /**
     * 查询设备列表-分页
     * @param rdc
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/relation_devicecontrolmaplistbyconditionandpage")
    public Result getRelation_DeviceControlMapMapperListByConditionAndPage(Relation_DeviceControlMap rdc, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.getSuccessResult(new PageInfo(rdcMapper.getRelation_DeviceControlMapListByCondition(rdc)));
    }

    /**
     * 编辑设备
     * @param rdc
     * @return
     */
    @PostMapping("/editrelation_devicecontrolmap")
    public Result editRelation_DeviceControlMap(@RequestBody Relation_DeviceControlMap rdc){
        if(rdc.getId()!=null){
            rdcMapper.updateRelation_DeviceControlMap(rdc);
        }else{
            Timestamp d = new Timestamp(System.currentTimeMillis());
            rdc.setCreateDatetime(d);
            rdcMapper.insertRelation_DeviceControlMap(rdc);
        }
        return Result.getSuccessResult();
    }

    /**
     * 删除设备
     * @param id
     * @return
     */
    @PostMapping(value = "/deleterelation_devicecontrolmapbyid")
    public Result deleteRelation_DeviceControlMapById(@RequestParam int id){
        rdcMapper.deleteRelation_DeviceControlMap(id);
        return Result.getSuccessResult();
    }

}

