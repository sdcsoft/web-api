package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceFileMapper;
import cn.com.sdcsoft.webapi.wechat.entity.DeviceFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 产品信息
 */
@RestController
@RequestMapping(value = "/webapi/devicefile", produces = "application/json;charset=utf-8")
@Auth
public class Wechat_DeviceFileController {

    @Autowired
    Wechat_DB_DeviceFileMapper wechat_db_deviceFileMapper;


    /**
     * 获得列表
     *
     * @param deviceFile
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/search")
    public Result search(@RequestBody DeviceFile deviceFile, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.getSuccessResult(new PageInfo(wechat_db_deviceFileMapper.getDeviceFileListByCondition(deviceFile)));
    }


    /**
     * 编辑
     *
     * @param deviceFile
     * @return
     */
    @PostMapping(value = "/modify")
    public Result updateDeviceFileById(@RequestBody DeviceFile deviceFile) {
        wechat_db_deviceFileMapper.updateDeviceFileById(deviceFile);
        return Result.getSuccessResult();
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/remove")
    public Result deleteDeviceFile(@RequestParam String id) {
        wechat_db_deviceFileMapper.deleteDeviceFile(id);
        return Result.getSuccessResult();
    }

}
