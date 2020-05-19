package cn.com.sdcsoft.webapi.web.enterprisemanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_PartSubCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 辅机小类信息
 */
@RestController
@RequestMapping(value = "/webapi/enterprise/partsubcategory", produces = "application/json;charset=utf-8")
@Auth
public class EnterpriseManage_PartSubCategoryController {

    @Autowired
    Enterprise_DB_PartSubCategoryMapper partSubCategoryMapper;

    /**
     * 查询数据列表-不带分页
     *
     * @param partCategoryId
     * @return
     */
    @GetMapping(value = "/list")
    public Result getList(Integer partCategoryId) {
        return Result.getSuccessResult(partSubCategoryMapper.findAllPartCategoryId(partCategoryId));
    }
}
