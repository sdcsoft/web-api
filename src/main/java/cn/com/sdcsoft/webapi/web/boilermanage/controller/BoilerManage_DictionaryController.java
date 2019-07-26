package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.Dictionary;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典信息
 */
@RestController
@RequestMapping(value = "/webapi/boilermanage/dictionary", produces = "application/json;charset=utf-8")
public class BoilerManage_DictionaryController {

    @Autowired
    private DictionaryMapper dictionaryMapper;

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
