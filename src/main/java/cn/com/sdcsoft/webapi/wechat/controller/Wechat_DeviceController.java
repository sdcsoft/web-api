package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.commservice.DeviceCacheService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceUserControlMapMapper;
import cn.com.sdcsoft.webapi.wechat.controller.utils.AesCbcUtil;
import cn.com.sdcsoft.webapi.wechat.client.TemplateClient;
import com.alibaba.fastjson.JSONObject;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信设备信息相关功能接口
 */
@RestController
@RequestMapping(value = "/wechat/device")
public class Wechat_DeviceController {

    //    @Value("${device.cache}")
//    private String deviceCacheUrl;

    @Autowired
    LAN_API lan_api;

    @Autowired
    DeviceCacheService deviceCacheService;

    @Value("${device.command}")
    private String deviceCommandUrl;


    @Value("${wechat.wx-openid}")
    private String wxOpenIdUrl;


    @GetMapping(value = "/getopenid")
    public String getOpenId(String js_code) {
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl, "/sns/jscode2session"));
        Map<String, String> map = new HashMap<>();
        map.put("appid", "wxec5096c52525bffb");
        map.put("secret", "b23d65a821c165ed7cc9b7e56b501b6e");
        map.put("js_code", js_code);
        map.put("grant_type", "authorization_code");
        return wxClient.post(map);
    }

    @GetMapping(value = "/wxshow/getopenid")
    public String getWxShowOpenId(String js_code) {
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl,"/sns/jscode2session"));
        Map<String,String> map=new HashMap<>();
        map.put("appid","wx41799f23dc99b67b");
        map.put("secret","9f147c3e4d6acd5a36121f1b8317cd15");
        map.put("js_code",js_code);
        map.put("grant_type","authorization_code");
        return wxClient.post(map);
    }

    @GetMapping(value = "/getUnionId")
    public Result getUnionId(String encryptedData, String iv, String code) {
        if (code == null || code.length() == 0) {
            return Result.getFailResult("code 不能为空");
        }
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl, "/sns/jscode2session"));
        Map<String, String> map = new HashMap<>();
        map.put("appid", "wxec5096c52525bffb");
        map.put("secret", "b23d65a821c165ed7cc9b7e56b501b6e");
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        JSONObject json = JSONObject.parseObject(wxClient.post(map));
        String session_key = json.get("session_key").toString();
        Map<String, String> data = new HashMap<>();
        String openid = (String) json.get("openid");
        data.put("openid", openid);
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                JSONObject json1 = JSONObject.parseObject(result);
                String unionid = (String) json1.get("unionId");
                data.put("unionId", unionid);
            } else {
                return Result.getFailResult("解密失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getSuccessResult(data);
    }

    @GetMapping(value = "/modify", produces = {"application/json;charset=UTF-8"})
    public String modifyDevice(String deviceNo, int prefix, String deviceType, int status, int power, int media, String iMEI,Integer isCanCtl,Integer cnId,Integer enId) {

        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", "http://simapi.sdcsoft.com.cn","/sim/wechat/update"));
        Map<String,String> map=new HashMap<>();
        map.put("simNo",iMEI);
        map.put("topupState","0");
         wxClient.post(map);
        return lan_api.deviceModifyForEnterpriseUser(deviceNo, prefix, deviceType, status, power, media, iMEI,isCanCtl,cnId,enId);
    }

    @GetMapping(value = "/modify/type", produces = {"application/json;charset=UTF-8"})
    public String deviceModifyType(String suffix, String deviceType, String subType) {
        return lan_api.deviceModifyType(suffix, deviceType, subType);
    }

    @GetMapping(value = "/getsuffix", produces = {"application/json;charset=UTF-8"})
    public String getSuffix(String deviceNo) {
        return lan_api.deviceFindBySuffixForEnterpriseUser(deviceNo);
    }

    @GetMapping(value = "/type/list", produces = {"application/json;charset=UTF-8"})
    public String getTypeList() {
        return lan_api.deviceTypeList();
    }

    @GetMapping(value = "/sendcmd")
    public String sendCmd(String command, String deviceSuffix, String userId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("DeviceSuffix", deviceSuffix);
        map.put("UserId", userId);
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", deviceCommandUrl, "/commands/send"));
        Map<String, String> mapp = new HashMap<>();
        mapp.put("command", command);
        return wxClient.post(mapp, map);
    }

    @Autowired
    private Wechat_DB_DeviceUserControlMapMapper rdcMapper;

    @GetMapping(value = "/control/List")
    public Result getControlList(String openid) {
        return Result.getSuccessResult(rdcMapper.getDeviceUserControlMapListByopenId(openid));
    }

    @GetMapping(value = "/getdata")
    public byte[] getData(String deviceNo) {
        return deviceCacheService.getDeviceCacheData(deviceNo);
    }
    @GetMapping(value = "/getdecode", produces = {"application/json;charset=UTF-8"})
    public String getDecode(String deviceNo) {
        return lan_api.deviceFindByDeviceNo(deviceNo);
    }

}
