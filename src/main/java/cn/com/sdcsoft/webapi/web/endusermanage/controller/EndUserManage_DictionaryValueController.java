package cn.com.sdcsoft.webapi.web.endusermanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_DictionaryValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典对应值
 */
@RestController
@RequestMapping(value = "/webapi/endusermanage/dictionaryvalue", produces = "application/json;charset=utf-8")
public class EndUserManage_DictionaryValueController {

    @Autowired
    private EndUser_DB_DictionaryValueMapper dictionaryValueMapper;


    @GetMapping(value = "/list")
    public Result getDictionaryValueListByType(String type) {
        return Result.getSuccessResult(dictionaryValueMapper.findByType(type));
    }

}
