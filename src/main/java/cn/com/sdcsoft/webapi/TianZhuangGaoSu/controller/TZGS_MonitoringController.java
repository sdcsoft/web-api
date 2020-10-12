package cn.com.sdcsoft.webapi.TianZhuangGaoSu.controller;

import cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper.MonitoringMapper;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = {"/tzgs/monitoring"})
public class TZGS_MonitoringController {

    @Autowired
    private MonitoringMapper monitoringMapper;


    @GetMapping(value = "/list")
    public Result find() {
        return Result.getSuccessResult(monitoringMapper.find());
    }

 



}

