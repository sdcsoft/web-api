package cn.com.sdcsoft.webapi.web.devicetojson.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.utils.DeviceJsonObjectCacheUtil;
import cn.com.sdcsoft.webapi.web.devicetojson.service.DeviceUuidService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device/json")
public class DeviceToJsonController {

    static final String SignKey ="1edsjif233sxjkfd@wiorew#fds08";
    @Autowired
    LAN_API lan_api;
    @Autowired
    DeviceJsonObjectCacheUtil deviceJsonObjectCacheUtil;
    @Autowired
    DeviceUuidService service;

    @RequestMapping("/map/id")
    @Cacheable(value = "deviceMapPointMap", key = "#deviceNo", unless = "#result.code != 0 ")
    public Result getDevicePointMapId(String deviceNo) {
        try {
            JSONObject result = JSONObject.parseObject(lan_api.deviceFindByDeviceNo(deviceNo));
            if (Result.RESULT_CODE_SUCCESS == result.getIntValue("code")) {
                JSONObject device = result.getJSONObject("data");
                Integer mapCnId = device.getInteger("deviceDataMapCn");
                Integer mapEnId = device.getInteger("deviceDataMapEn");
                if (null != mapCnId && mapCnId > 0) {
                    return Result.getSuccessResult(mapCnId);
                } else if (null != mapEnId && mapEnId > 0) {
                    return Result.getSuccessResult(mapEnId);
                }
                return Result.getFailResult("device haven't map.");
            }
            return Result.getFailResult(result.getString("msg"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.getFailResult(ex.getMessage());
        }
    }

    @RequestMapping("/map/info")
    @Cacheable(value = "devicePointMap", key = "#pointMapId", unless = "#result.code != 0")
    public Result getPonitMap(Integer pointMapId) {
        try {
            return lan_api.dataMapGet(pointMapId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.getFailResult(ex.getMessage());
        }
    }


    @PostMapping("/save")
    public Result saveDeviceJson(String signKey, @RequestParam String deviceNo, @RequestBody JSONObject json) {
        if (!signKey.equals(SignKey)) {
            return Result.getFailResult("signKey error.");
        }

        deviceJsonObjectCacheUtil.putCacheItem(deviceNo, json);
        return Result.getSuccessResult();

    }

    @RequestMapping("/get")
    public Result getDeviceJsonObject(String uuid){
        String deviceNo = service.getDeviceNo(uuid);
        if(null == deviceNo){
            return Result.getFailResult("设备UUID无效。");
        }
        return Result.getSuccessResult(deviceJsonObjectCacheUtil.getCacheItem(deviceNo));
    }
}
