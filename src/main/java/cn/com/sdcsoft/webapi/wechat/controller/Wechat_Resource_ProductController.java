package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_ResourceMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_Resource_ProductMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_Role_ResourceMapper;
import cn.com.sdcsoft.webapi.wechat.entity.Resource_Product;
import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/webapi/wechat/Resource_Product")
public class Wechat_Resource_ProductController {

    @Autowired
    private Wechat_DB_Resource_ProductMapper wechat_db_resource_productMapper;

    @Autowired
    private Wechat_DB_ResourceMapper wechat_db_resourceMapper;


    @GetMapping(value = "/Resource/list")
    public Result getResourcelist() {
        return Result.getSuccessResult(wechat_db_resourceMapper.getResourceList());
    }

    @GetMapping(value = "/list")
    public Result getResource_Productlist() {
        return Result.getSuccessResult(wechat_db_resource_productMapper.getResource_ProductList());
    }


    @PostMapping("/create")
    public Result editRole_Resource(@RequestBody Resource_Product resource_product) {
        wechat_db_resource_productMapper.insertResource_ProductResource_Product(resource_product);
        return Result.getSuccessResult();
    }

    @PostMapping("/modify")
    public Result modifyRole_Resource(@RequestBody Resource_Product resource_product) {
        wechat_db_resource_productMapper.updateResource_Product(resource_product);
        return Result.getSuccessResult();
    }

    @GetMapping(value = "/remove")
    public Result deleteRole_Resource(Integer openId) {
        wechat_db_resource_productMapper.deleteResource_Product(openId);
        return Result.getSuccessResult();
    }

}

