package cn.com.sdcsoft.webapi.web.enterprisemanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_DictionaryMapper;
import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.Dictionary;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典信息
 */
@RestController
@RequestMapping(value = "/webapi/enterprise/dictionary", produces = "application/json;charset=utf-8")
public class EnterpriseManage_DictionaryController {

    @Autowired
    private Enterprise_DB_DictionaryMapper dictionaryMapper;

    /**
     * 查询字典列表
     *
     * @param dictionary
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public Result list(Dictionary dictionary, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dictionary> list = dictionaryMapper.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.getSuccessResult(pageInfo);
    }


}
