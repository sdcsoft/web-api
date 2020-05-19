package cn.com.sdcsoft.webapi.web.enterprisemanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_DictionaryValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典对应值
 */
@RestController
@RequestMapping(value = "/webapi/enterprise/dictionaryvalue", produces = "application/json;charset=utf-8")
public class EnterpriseManage_DictionaryValueController {

    @Autowired
    private Enterprise_DB_DictionaryValueMapper dictionaryValueMapper;


    @GetMapping(value = "/list")
    public Result getDictionaryValueListByType(String type) {
        return Result.getSuccessResult(dictionaryValueMapper.findByType(type));
    }

}
