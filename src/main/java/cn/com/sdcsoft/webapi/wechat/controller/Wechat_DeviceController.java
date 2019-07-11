package cn.com.sdcsoft.webapi.wechat.controller;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.client.TemplateClient;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
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
    private static String deviceCommandUrl;


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
    @GetMapping(value = "/sendcmd")
    public String sendcmd(String command,HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            if(key.equals("userid") ||key.equals("devicesuffix")){
                map.put(key, value);
            }
        }
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", deviceCommandUrl,"/commands/send"));
        Map<String,String> mapp=new HashMap<>();
        mapp.put("command",command);
        return wxClient.post(mapp,map);
    }
}