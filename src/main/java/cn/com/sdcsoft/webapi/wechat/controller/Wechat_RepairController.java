package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping(value = "/webapi/wechat/Repair")
public class Wechat_RepairController {


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

