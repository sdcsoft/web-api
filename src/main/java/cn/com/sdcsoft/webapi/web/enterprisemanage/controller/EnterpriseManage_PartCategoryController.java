package cn.com.sdcsoft.webapi.web.enterprisemanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_PartCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 辅机大类信息
 */
@RestController
@RequestMapping(value = "/webapi/enterprise/partcategory", produces = "application/json;charset=utf-8")
@Auth
public class EnterpriseManage_PartCategoryController {

    @Autowired
    Enterprise_DB_PartCategoryMapper partCategoryMapper;

    /**
     * 查询数据列表-不带分页
     *
     * @return
     */
    @GetMapping(value = "/list")
    public Result getList() {
        try {
            return Result.getSuccessResult(partCategoryMapper.findAll());
        } catch (Exception ex) {
            return Result.getFailResult(ex.getMessage());
        }
    }
}
