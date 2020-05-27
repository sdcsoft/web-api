package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.SoldProductSearchOptions;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_ProductMapper;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_ProductPartInfoMapper;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_ProductUserMapper;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_UserMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.com.sdcsoft.webapi.web.boilermanage.service.ProductService;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 产品信息
 */
@RestController
@RequestMapping(value = "/webapi/boilermanage/product", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_ProductController {

    @Autowired
    LAN_API lan_api;

    @Autowired
    Customer_DB_ProductMapper productMapper;

    @Autowired
    Customer_DB_ProductUserMapper productUserMapper;

    @Autowired
    ProductService productService;

    @Autowired
    Customer_DB_ProductPartInfoMapper productAuxiliaryMachineInfoMapper;

    /**
     * 获得产品列表数据
     *
     * @param product
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/search")
    public Result search(@RequestBody Product product, int pageNum, int pageSize, HttpServletRequest request) {
        Integer employeeId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_EmployeeID).toString());
        User user = userMapper.findUserByEmployeeId(employeeId);
        PageHelper.startPage(pageNum, pageSize);
        if (user.getRoleId() == Role.SYSTEM_ADMIN_ROLE_ID) {
            List<Product> list = productMapper.searchForAdmin(user.getOrgId(), product, false);
            PageInfo pageInfo = new PageInfo(list);
            return Result.getSuccessResult(pageInfo);
        } else {
            List<Product> list = productMapper.search(user.getId(), product);
            PageInfo pageInfo = new PageInfo(list);
            return Result.getSuccessResult(pageInfo);
        }
    }

    /**
     * 获得所分配的用户
     *
     * @param productId
     * @return
     */
    @GetMapping("/users")
    public Result getUsers(int productId) {
        return Result.getSuccessResult(productUserMapper.getProductUserListByProduct(productId));
    }

    /**
     * 为产品分配用户
     *
     * @param productId
     * @param productUsers
     * @return
     */
    @PostMapping("/users/modify")
    public Result modifyProductUsers(int productId, @RequestBody List<ProductUser> productUsers) {
        productUserMapper.clearProductMap(productId);
        if (productUsers.size() > 0) {
            productUserMapper.createProductUserMap(productUsers);
        }
        return Result.getSuccessResult();
    }

    /**
     * 获取售出的产品信息
     *
     * @param searchOptions
     * @param request
     * @return
     */
    @PostMapping("/sold")
    public Result sold(@RequestBody SoldProductSearchOptions searchOptions, HttpServletRequest request) {
        Integer employeeId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_EmployeeID).toString());
        User user = userMapper.findUserByEmployeeId(employeeId);
        if (user.getRoleId() == Role.SYSTEM_ADMIN_ROLE_ID) {
            return Result.getSuccessResult(productMapper.findSoldForAdmin(user.getOrgId(), searchOptions));
        } else {
            return Result.getSuccessResult(productMapper.find(user.getId(), searchOptions));
        }
    }

    /**
     * 编辑产品
     *
     * @param product
     * @return
     */
    @PostMapping("/modify")
    public Result editProduct(@RequestBody Product product) {
        productMapper.modifyProductInfo(product);
        return Result.getSuccessResult();
    }

    @PostMapping("/sell")
    public Result sellProduct(@RequestBody Product product) {
        productMapper.modifyProductSellInfo(product);
        return Result.getSuccessResult();
    }

    @Autowired
    Customer_DB_UserMapper userMapper;

    /**
     * 插入产品数据
     *
     * @param product
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody Product product, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        try {
            JSONObject obj = JSONObject.parseObject(lan_api.deviceModifyCustomerId(product.getControllerNo(), orgId));
            product.setOrgId(orgId);
            Integer employeeId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_EmployeeID).toString());
            User user = userMapper.findUserByEmployeeId(employeeId);
            if (user.getRoleId() == Role.SYSTEM_ADMIN_ROLE_ID) {
                productService.createProduct(product);
            } else {
                productService.createProduct(product, user.getId());
            }
            return Result.getSuccessResult();
        }
        catch (Exception ex) {
            return Result.getFailResult(ex.getMessage());
        }
    }


    /**
     * 删除产品信息
     *
     * @param id
     * @return
     */
    @PostMapping("/remove")
    public Result deleteProductById(@RequestParam int id, @RequestParam String controllerNo) {
        int code = productService.deleteProduct(id, controllerNo);
        if (0 == code) {
            lan_api.deviceModifyCustomerId(controllerNo, null);
            return Result.getSuccessResult();
        } else
            return Result.getFailResult("删除失败");
    }
}
