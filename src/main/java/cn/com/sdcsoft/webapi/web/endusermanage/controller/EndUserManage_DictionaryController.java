package cn.com.sdcsoft.webapi.web.endusermanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_DictionaryMapper;
import cn.com.sdcsoft.webapi.web.endusermanage.entity.Dictionary;
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
@RequestMapping(value = "/webapi/endusermanage/dictionary", produces = "application/json;charset=utf-8")
public class EndUserManage_DictionaryController {

    @Autowired
    private EndUser_DB_DictionaryMapper dictionaryMapper;

    /**
     * 查询字典列表
     * @param dictionary
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public Result list(Dictionary dictionary, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dictionary> list =dictionaryMapper.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.getSuccessResult(pageInfo);
    }


}
