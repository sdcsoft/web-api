package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DeviceControlMapMapper;
import cn.com.sdcsoft.webapi.wechat.client.TemplateClient;
import cn.com.sdcsoft.webapi.wechat.entity.Relation_DeviceControlMap;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信设备信息相关功能接口
 */
@RestController
@RequestMapping(value = "/wechat/device")
public class Wechat_DeviceController {

    @Value("${device.cache}")
    private String deviceCacheUrl;

    @Value("${device.command}")
    private  String deviceCommandUrl;


    @Value("${wechat.wx-openid}")
    private String wxOpenIdUrl;

    @GetMapping(value = "/getdata")
    public byte[] getWx(String deviceNo) {
        TemplateClient deviceInfoClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", deviceCacheUrl,"/device2/get2"));
        Map<String,String> map=new HashMap<>();
        map.put("id",deviceNo);
        return deviceInfoClient.getBytes(map);
    }

    @GetMapping(value = "/getopenid")
    public String getOpenId(String js_code) {
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl,"/sns/jscode2session"));
        Map<String,String> map=new HashMap<>();
        map.put("appid","wxec5096c52525bffb");
        map.put("secret","b23d65a821c165ed7cc9b7e56b501b6e");
        map.put("js_code",js_code);
        map.put("grant_type","authorization_code");
        return wxClient.post(map);
    }
    @Autowired
    LAN_API lan_api;
    @GetMapping(value = "/getdecode", produces = { "application/json;charset=UTF-8" })
    public String getdecode(String deviceNo) {
        return lan_api.deviceFindByDeviceNo(deviceNo);
    }


    @GetMapping(value = "/modifydevice", produces = { "application/json;charset=UTF-8" })
    public String modifydevice(String deviceNo,int prefix,String deviceType,int status,int power,int media) {
        return lan_api.deviceModifyForEnterpriseUser(deviceNo,prefix,deviceType,status,power,media);
    }

    @GetMapping(value = "/getsuffix", produces = { "application/json;charset=UTF-8" })
    public String getsuffix(String deviceNo) {
        return lan_api.deviceFindBySuffixForEnterpriseUser(deviceNo);
    }

    @GetMapping(value = "/gettypelist", produces = { "application/json;charset=UTF-8" })
    public String gettypelist() {
        return lan_api.deviceTypeList();
    }

    @GetMapping(value = "/sendcmd")
    public String sendcmd(String command,String deviceSuffix,String userId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("DeviceSuffix",deviceSuffix);
        map.put("UserId",userId);
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", deviceCommandUrl,"/commands/send"));
        Map<String,String> mapp=new HashMap<>();
        mapp.put("command",command);
        return wxClient.post(mapp,map);
    }
    @Autowired
    private Wechat_DB_DeviceControlMapMapper rdcMapper;

    @GetMapping(value = "/getdevicecontrolList")
    public Result getdevicecontrolList(String openid) {
        Result result = lan_api.employeeFindWechat(openid);
        LinkedHashMap data=(LinkedHashMap)result.getData();
        Relation_DeviceControlMap rdc =new Relation_DeviceControlMap();
        rdc.setEmployeeMobile(data.get("mobile").toString());
        return Result.getSuccessResult(rdcMapper.getRelation_DeviceControlMapListByCondition(rdc));
    }
}
