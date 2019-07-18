package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.RepairInfo;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.RepairInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 *
 */
@RestController
@RequestMapping(value = "/webapi/boiler/repairinfo", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_RepairInfoController {

    @Autowired
    RepairInfoMapper repairInfoMapper;

    @GetMapping(value = "/list/product")
    public Result getRepairInfoListByProductId(Integer productId){
        return  Result.getSuccessResult(repairInfoMapper.getProductRepairInfos(productId));
    }
    @GetMapping(value = "/list/user")
    public Result getRepairInfoListByUserId(Integer userId){
        return  Result.getSuccessResult(repairInfoMapper.getUserRepairInfos(userId));
    }
    @GetMapping(value = "/list/product/date")
    public Result getRepairInfoListByDate(Integer productId, Timestamp startTime, Timestamp endTime){
        return  Result.getSuccessResult(repairInfoMapper.getProductRepairInfosByDate( productId, startTime, endTime));
    }
    @GetMapping(value = "/list/user/date")
    public Result getRepairInfoListBydate(Integer userId, Timestamp startTime, Timestamp endTime){
        return  Result.getSuccessResult(repairInfoMapper.getUserRepairInfosByDate( userId, startTime, endTime));
    }

   @PostMapping(value = "/create")
    public Result insertRepairInfo(@RequestBody RepairInfo repairInfo){
       repairInfoMapper.insertRepairInfo(repairInfo);
        return Result.getSuccessResult();
    }
    @PostMapping(value = "/remove")
    public Result deleteRepairInfoByProductId(@RequestParam int id){
        repairInfoMapper.removeRepairInfoById( id);
        return Result.getSuccessResult();
    }
}
