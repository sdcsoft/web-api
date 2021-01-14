package cn.com.sdcsoft.webapi.web.enterprisemanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_ProductMapper;
import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.ProductCategory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 锅炉型号
 */
@RestController
@RequestMapping(value = "/webapi/enterprise/productcategory", produces = "application/json;charset=utf-8")
@Auth
public class EnterpriseManage_ProductCategoryController {

    @Autowired
    private Enterprise_DB_ProductMapper productCategoryMapper;


    /**
     * 查询数据-分页
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/pagelist")
    public Result getBoilerModelListByConditionAndPage(int pageNum, int pageSize, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());

        PageHelper.startPage(pageNum, pageSize);
        List<String> list = productCategoryMapper.getProductCategories(orgId);
        PageInfo pageInfo = new PageInfo(list);
        return Result.getSuccessResult(pageInfo);

    }

    /**
     * 查询数据-不带分页
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    public Result list(HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        return Result.getSuccessResult(productCategoryMapper.getProductCategories(orgId));
    }

    /**
     * 创建
     *
     * @param productCategory
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody ProductCategory productCategory, HttpServletRequest request) {
//        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
//
//        productCategory.setOrgId(orgId);
//        productCategoryMapper.createProductCategory(productCategory);
        return Result.getFailResult("不支持手动创建分类！");
    }


    /**
     * 编辑数据
     *
     * @param productCategory
     * @return
     */
    @PostMapping("/modify")
    public Result modify(@RequestBody ProductCategory productCategory) {
        //productCategoryMapper.modifyProductCategory(productCategory);
        return Result.getFailResult("不支持修改分类！");
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteboilermodelbyid")
    public Result deleteBoilerModelById(@RequestParam int id) {
        return Result.getFailResult("不支持删除分类！");
//        if (0 < productCategoryMapper.checkProductCountOfTheCategory(id))
//            return Result.getFailResult("不能删除正在被使用的数据！");
//
//        productCategoryMapper.removeById(id);
//        return Result.getSuccessResult();
    }
}
