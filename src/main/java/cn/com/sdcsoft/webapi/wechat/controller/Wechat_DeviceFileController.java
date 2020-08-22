package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceFileMapper;
import cn.com.sdcsoft.webapi.wechat.entity.DeviceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

/**
 * 产品信息
 */
@RestController
@RequestMapping(value = "/webapi/devicefile", produces = "application/json;charset=utf-8")
public class Wechat_DeviceFileController {

    @Autowired
    Wechat_DB_DeviceFileMapper wechat_db_deviceFileMapper;



    @GetMapping(value = "/list")
    public Result getDeviceFileByDeviceNo(String deviceNo) {
        return Result.getSuccessResult(wechat_db_deviceFileMapper.getDeviceFileByDeviceNo(deviceNo));
    }



}
