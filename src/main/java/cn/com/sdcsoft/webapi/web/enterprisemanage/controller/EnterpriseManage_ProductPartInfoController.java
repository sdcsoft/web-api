package cn.com.sdcsoft.webapi.web.enterprisemanage.controller;


import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_ProductPartInfoMapper;
import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.ProductPartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品辅机信息
 */
@RestController
@RequestMapping(value = "/webapi/enterprise/productpartinfo", produces = "application/json;charset=utf-8")
@Auth
public class EnterpriseManage_ProductPartInfoController {

    @Autowired
    Enterprise_DB_ProductPartInfoMapper productPartInfoMapper;

    @GetMapping(value = "/list")
    public Result getProductPartInfos(Integer productId) {
        return Result.getSuccessResult(productPartInfoMapper.findByProductId(productId));
    }

    @PostMapping(value = "/create")
    public Result createProductPartInfos(@RequestBody List<ProductPartInfo> productPartInfos) {
        if (productPartInfos.size() > 0) {
            productPartInfoMapper.createProductPartInfoList(productPartInfos);
        }
        return Result.getSuccessResult();
    }

    @PostMapping(value = "/modify")
    public Result modifyProductPartInfo(@RequestBody ProductPartInfo productPartInfo) {
        productPartInfoMapper.modifyProductPartInfo(productPartInfo);
        return Result.getSuccessResult();
    }

    @PostMapping(value = "/remove")
    public Result removeProductPartInfo(int productId, int productPartInfoId) {
        productPartInfoMapper.removeProductPartInfo(productId, productPartInfoId);
        return Result.getSuccessResult();
    }

}
