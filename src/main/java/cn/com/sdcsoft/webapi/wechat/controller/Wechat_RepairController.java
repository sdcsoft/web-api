package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_ProductMapper;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_RepairInfoMapper;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_UserMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.Product;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.RepairInfo;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;


@RestController
@RequestMapping(value = "/wechat/Repair")
public class Wechat_RepairController {

    @Autowired
    LAN_API lan_api;

    @Autowired
    private Customer_DB_RepairInfoMapper customer_db_repairInfoMapper;

    @Autowired
    private Customer_DB_UserMapper customer_db_userMapper;

    @Autowired
    private Customer_DB_ProductMapper customer_db_productMapper;



    @PostMapping("/create")
    public Result editRepair(@RequestBody RepairInfo repairInfo) {
        Result result = lan_api.findWeChatEmployee(repairInfo.getUserName());
        ArrayList arrayList=(ArrayList)result.getData();

        LinkedHashMap json = (LinkedHashMap) arrayList.get(0);
        User user=customer_db_userMapper.findUserByEmployeeId((Integer) json.get("id"));
        if(user==null){
            return Result.getFailResult("请您是否为有效维保人员");
        }
        Product p=  customer_db_productMapper.findProductByorgId(user.getOrgId(),repairInfo.getBoilerNo());
        if(p==null){
            return Result.getFailResult("维保信息录入失败");
        }
        repairInfo.setProductId(p.getId());
        repairInfo.setCreateUserId(user.getEmployeeId());
        repairInfo.setUserId(user.getId());
        repairInfo.setUserName(user.getUserName());
        Timestamp d = new Timestamp(System.currentTimeMillis());
        repairInfo.setCreateDatetime(d);
        repairInfo.setRepairDatetime(d);
        repairInfo.setCreateUserName( json.get("realName").toString());
        customer_db_repairInfoMapper.insertRepairInfo(repairInfo);
        return Result.getSuccessResult(repairInfo);
    }


    @ResponseBody
    @PostMapping(value = "/uploadFile")
    public static Result fileSave(@RequestParam("file")MultipartFile file, @RequestParam("repairId")String repairId){
        //定义文件保存的本地路径
        if(!new File(repairId).exists()){
            new File(repairId).mkdirs();
        }
        //定义 文件名
        String filename=null;
        //判断是否为空
        if(!file.isEmpty()){
            //生成uuid作为文件名称
            String uuid = UUID.randomUUID().toString();
            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType=file.getContentType();
            //获得文件后缀名
            String suffixName=contentType.substring(contentType.indexOf("/")+1);
            //得到文件名
            filename=uuid+"."+suffixName;
            //文件保存路径
            try {
                //将文件转移到指定位置
                file.transferTo(new File(repairId+filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  Result.getSuccessResult();
    }
}

