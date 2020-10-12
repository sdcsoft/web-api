package cn.com.sdcsoft.webapi.TianZhuangGaoSu.controller;

import cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity.ControlLog;
import cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper.ControlLogMapper;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = {"/tzgs/controllog"})
public class TZGS_ControlLogController {

    @Autowired
    private ControlLogMapper controlLogMapper;




    @GetMapping(value = "/list")
    public Result find() {
        return Result.getSuccessResult(controlLogMapper.find());
    }


    @PostMapping("/create")
    public Result create(@RequestBody ControlLog controlLog) {
        controlLogMapper.insert(controlLog);
        return Result.getSuccessResult();
    }


}

