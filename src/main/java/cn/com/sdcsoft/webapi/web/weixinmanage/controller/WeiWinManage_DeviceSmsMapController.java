package cn.com.sdcsoft.webapi.web.weixinmanage.controller;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceSmsMapMapper;
import cn.com.sdcsoft.webapi.web.weixinmanage.entity.Relation_DeviceSmsMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RestController
@RequestMapping(value = "/webapi/weixinmanage/deviceSms")
public class WeiWinManage_DeviceSmsMapController {
    @Autowired(required=true)
    private Wechat_DB_DeviceSmsMapMapper deviceSmsMapMapper;





    /**
     * 查询设备列表-分页
     * @param rdc
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/relation_deviceSmslistbyconditionandpage")
    public Result getRelation_DeviceSmsMapMapperListByConditionAndPage(Relation_DeviceSmsMap rdc, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.getSuccessResult(new PageInfo(deviceSmsMapMapper.getRelation_DeviceSmsMapListByCondition(rdc)));
    }

    /**
     * 查询设备列表
     * @param rdc
     * @return
     */
    @GetMapping(value = "/relation_deviceSmslistbycondition")
    public Result getRelation_DeviceSmsMapMapperListByCondition(Relation_DeviceSmsMap rdc) {
        return Result.getSuccessResult(deviceSmsMapMapper.getRelation_DeviceSmsMapList(rdc));
    }
    /**
     * 编辑设备
     * @param rdc
     * @return
     */
    @PostMapping("/editrelation_DeviceSmsMap")
    public Result editRelation_DeviceSmsMap(@RequestBody Relation_DeviceSmsMap rdc){
        if(rdc.getId()!=null){
            deviceSmsMapMapper.updateRelation_DeviceSmsMap(rdc);
        }else{
            Timestamp d = new Timestamp(System.currentTimeMillis());
            rdc.setCreateDatetime(d);
            deviceSmsMapMapper.insertRelation_DeviceSmsMap(rdc);
        }
        return Result.getSuccessResult();
    }

    /**
     * 删除设备
     * @param id
     * @return
     */
    @PostMapping(value = "/deleterelation_DeviceSmsMapbyid")
    public Result deleteRelation_DeviceSmsMapById(@RequestParam int id){
        deviceSmsMapMapper.deleteRelation_DeviceSmsMap(id);
        return Result.getSuccessResult();
    }

}

