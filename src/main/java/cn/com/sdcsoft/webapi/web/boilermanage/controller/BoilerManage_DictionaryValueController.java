package cn.com.sdcsoft.webapi.web.boilermanage.controller;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.DictionaryValue;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.DictionaryValueMapper;

import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 字典对应值
 */
@RestController
@RequestMapping(value = "/webapi/boiler/dictionaryvalue", produces = "application/json;charset=utf-8")
public class BoilerManage_DictionaryValueController {

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;


    @GetMapping(value = "/list")
    public Result getDictionaryValueListByType(String type){
        return  Result.getSuccessResult(dictionaryValueMapper.findByType(type));
    }

}
