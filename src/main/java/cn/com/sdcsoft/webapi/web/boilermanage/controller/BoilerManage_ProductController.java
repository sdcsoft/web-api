package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.*;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.UserMapper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ProductPartInfoMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ProductMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ProductUserMapper;
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
    ProductMapper productMapper;

    @Autowired
    ProductUserMapper productUserMapper;

    @Autowired
    ProductService productService;

    @Autowired
    ProductPartInfoMapper productAuxiliaryMachineInfoMapper;

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
        if (user.getRoleId() == Role.SYSTEM_ADMIN_ROLE_ID) {
            List<Product> list = productMapper.searchForAdmin(user.getOrgId(), product);
            PageHelper.startPage(pageNum, pageSize);
            PageInfo pageInfo = new PageInfo(list);
            return Result.getSuccessResult(pageInfo);
        } else {
            List<Product> list = productMapper.search(user.getId(), product);
            PageHelper.startPage(pageNum, pageSize);
            PageInfo pageInfo = new PageInfo(list);
            return Result.getSuccessResult(pageInfo);
        }

    }

    /**
     * 临时模拟统计报表
     *
     * @param userId
     * @return
     */
    @GetMapping("/productTypeAmountByCondition")
    public Result typeCount(int userId) {
        List<ProductTypeAmountClass> list = productMapper.getProductTypeAmountByUserId(userId);
        return Result.getSuccessResult(list);
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
     * 展示数据在地图上
     *
     * @param product
     * @return
     */
    @GetMapping("/map")
    public Result map(@RequestBody Product product, HttpServletRequest request) {
        Integer employeeId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_EmployeeID).toString());
        User user = userMapper.findUserByEmployeeId(employeeId);
        if (user.getRoleId() == Role.SYSTEM_ADMIN_ROLE_ID) {
            return Result.getSuccessResult(productMapper.searchForAdmin(user.getOrgId(), product));
        } else {
            return Result.getSuccessResult(productMapper.search(user.getId(), product));
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
    UserMapper userMapper;

    /**
     * 插入产品数据
     *
     * @param product
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody Product product, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        JSONObject obj = JSONObject.parseObject(lan_api.deviceModifyCustomerId(product.getControllerNo(), orgId));
        if (0 == obj.getIntValue("code")) {
            product.setOrgId(orgId);
            Integer employeeId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_EmployeeID).toString());
            User user = userMapper.findUserByEmployeeId(employeeId);
            if (user.getRoleId() == Role.SYSTEM_ADMIN_ROLE_ID) {
                productService.createProduct(product);
            } else {
                productService.createProduct(product, user.getOrgId());
            }
            return Result.getSuccessResult();
        }
        return Result.getFailResult(obj.getString("msg"));
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
        if (0 == code){
            lan_api.deviceModifyCustomerId(controllerNo,null);
            return Result.getSuccessResult();
        }
        else
            return Result.getFailResult("删除失败");
    }
}