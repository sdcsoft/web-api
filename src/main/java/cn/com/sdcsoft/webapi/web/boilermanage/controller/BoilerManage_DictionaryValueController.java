package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_DictionaryValueMapper;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 字典对应值
 */
@RestController
@RequestMapping(value = "/webapi/boilermanage/dictionaryvalue", produces = "application/json;charset=utf-8")
public class BoilerManage_DictionaryValueController {

    @Autowired
    private Customer_DB_DictionaryValueMapper dictionaryValueMapper;


    @GetMapping(value = "/list")
    public Result getDictionaryValueListByType(String type) {
        return Result.getSuccessResult(dictionaryValueMapper.findByType(type));
    }

}
