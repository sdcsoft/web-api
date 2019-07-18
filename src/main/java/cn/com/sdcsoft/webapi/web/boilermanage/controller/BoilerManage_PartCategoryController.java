package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.PartCategory;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.PartCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 辅机大类信息
 */
@RestController
@RequestMapping(value = "/webapi/boiler/partcategory", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_PartCategoryController {

    @Autowired
    PartCategoryMapper partCategoryMapper;

    /**
     * 查询数据列表-不带分页
     * @return
     */
    @GetMapping(value = "/list")
    public Result getList() {
        try{
            return Result.getSuccessResult(partCategoryMapper.findAll());
        }
        catch (Exception ex){
            return Result.getFailResult(ex.getMessage());
        }
    }
}
