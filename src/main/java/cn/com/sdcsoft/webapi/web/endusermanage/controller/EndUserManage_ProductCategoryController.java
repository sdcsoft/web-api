package cn.com.sdcsoft.webapi.web.endusermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_ProductCategoryMapper;
import cn.com.sdcsoft.webapi.web.endusermanage.entity.ProductCategory;
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
@RequestMapping(value = "/webapi/endusermanage/productcategory", produces = "application/json;charset=utf-8")
@Auth
public class EndUserManage_ProductCategoryController {

    @Autowired
    private EndUser_DB_ProductCategoryMapper productCategoryMapper;


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
        List<ProductCategory> list = productCategoryMapper.findProductCategory(orgId);
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
        return Result.getSuccessResult(productCategoryMapper.findProductCategory(orgId));
    }

    /**
     * 创建
     *
     * @param productCategory
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody ProductCategory productCategory, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());

        productCategory.setOrgId(orgId);
        productCategoryMapper.createProductCategory(productCategory);
        return Result.getSuccessResult();
    }


    /**
     * 编辑数据
     *
     * @param productCategory
     * @return
     */
    @PostMapping("/modify")
    public Result modify(@RequestBody ProductCategory productCategory) {
        productCategoryMapper.modifyProductCategory(productCategory);
        return Result.getSuccessResult();
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteboilermodelbyid")
    public Result deleteBoilerModelById(@RequestParam int id) {
        if (0 < productCategoryMapper.checkProductCountOfTheCategory(id))
            return Result.getFailResult("不能删除正在被使用的数据！");

        productCategoryMapper.removeById(id);
        return Result.getSuccessResult();
    }
}
