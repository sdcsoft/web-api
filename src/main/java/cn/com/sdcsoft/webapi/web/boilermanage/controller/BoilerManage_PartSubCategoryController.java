package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.PartSubCategory;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.PartSubCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 辅机小类信息
 */
@RestController
@RequestMapping(value = "/webapi/boiler/partsubcategory", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_PartSubCategoryController {

    @Autowired
    PartSubCategoryMapper partSubCategoryMapper;

    /**
     * 查询数据列表-不带分页
     * @param partCategoryId
     * @return
     */
    @GetMapping(value = "/list")
    public Result getList(Integer partCategoryId) {
            return Result.getSuccessResult(partSubCategoryMapper.findAllPartCategoryId(partCategoryId));
    }
}
