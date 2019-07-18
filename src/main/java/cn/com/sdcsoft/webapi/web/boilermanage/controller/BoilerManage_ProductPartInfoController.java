package cn.com.sdcsoft.webapi.web.boilermanage.controller;


import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.ProductPartInfo;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ProductPartInfoMapper;

import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品辅机信息
 */
@RestController
@RequestMapping(value = "/webapi/boiler/productpartinfo", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_ProductPartInfoController {

    @Autowired
    ProductPartInfoMapper productPartInfoMapper;

    @GetMapping(value = "/list")
    public Result getProductPartInfos(Integer productId){
        return  Result.getSuccessResult(productPartInfoMapper.findByProductId(productId));
    }

    @PostMapping(value = "/create")
    public Result createProductPartInfos(@RequestBody List<ProductPartInfo> productPartInfos){
        if(productPartInfos.size()>0) {
            productPartInfoMapper.createProductPartInfoList(productPartInfos);
        }
        return Result.getSuccessResult();
    }

    @PostMapping(value = "/modify")
    public Result modifyProductPartInfo(@RequestBody ProductPartInfo productPartInfo){
        productPartInfoMapper.modifyProductPartInfo(productPartInfo);
        return Result.getSuccessResult();
    }
    @PostMapping(value = "/remove")
    public Result removeProductPartInfo(int productId,int productPartInfoId){
        productPartInfoMapper.removeProductPartInfo(productId,productPartInfoId);
        return Result.getSuccessResult();
    }

}
