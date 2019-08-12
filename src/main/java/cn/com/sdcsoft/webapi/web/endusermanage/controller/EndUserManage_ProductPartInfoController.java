package cn.com.sdcsoft.webapi.web.endusermanage.controller;


import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_ProductPartInfoMapper;
import cn.com.sdcsoft.webapi.web.endusermanage.entity.ProductPartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品辅机信息
 */
@RestController
@RequestMapping(value = "/webapi/endusermanage/productpartinfo", produces = "application/json;charset=utf-8")
@Auth
public class EndUserManage_ProductPartInfoController {

    @Autowired
    EndUser_DB_ProductPartInfoMapper productPartInfoMapper;

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
