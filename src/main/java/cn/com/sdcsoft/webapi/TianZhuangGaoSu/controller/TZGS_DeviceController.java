package cn.com.sdcsoft.webapi.TianZhuangGaoSu.controller;

import cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity.Device;
import cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper.DeviceMapper;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.wechat.client.TemplateClient;
import cn.com.sdcsoft.webapi.wechat.controller.utils.AesCbcUtil;
import com.alibaba.fastjson.JSONObject;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = {"/tzgs/device"})
public class TZGS_DeviceController {

    @Autowired
    private DeviceMapper deviceMapper;

    @Value("${wechat.wx-openid}")
    private String wxOpenIdUrl;

    @GetMapping(value = "/getopenid")
    public String getWxShowOpenId(String js_code) {
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl,"/sns/jscode2session"));
        Map<String,String> map=new HashMap<>();
        map.put("appid","wxc6113b95ebd4ff98");
        map.put("secret","83af7fdfa9164997f2273e3ea7284623");
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
        map.put("appid", "wxc6113b95ebd4ff98");
        map.put("secret", "83af7fdfa9164997f2273e3ea7284623");
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

    @GetMapping(value = "/list")
    public Result find() {
        return Result.getSuccessResult(deviceMapper.find());
    }

    @GetMapping(value = "/list/type")
    public Result findByType(String type) {
        return Result.getSuccessResult(deviceMapper.findByType(type));
    }

    @PostMapping("/create")
    public Result create(@RequestBody Device device) {
        deviceMapper.insert(device);
        return Result.getSuccessResult();
    }


}

