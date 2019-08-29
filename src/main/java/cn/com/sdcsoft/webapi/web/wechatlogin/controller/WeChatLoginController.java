package cn.com.sdcsoft.webapi.web.wechatlogin.controller;


import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.wechat.client.TemplateClient;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/wechatlogin")
public class WeChatLoginController {

    @Value("${wechat.wx-openid}")
    private String wxOpenIdUrl;

    @Autowired
    LAN_API lan_api;

    @RequestMapping(value="/login")
    public void goWeixinAuth(HttpServletResponse response) throws IOException {
        String redirect_url = "http://kuaixin.picp.net:14335/wechatlogin/weixinLoginCallback";
        String appId ="wxa614bd4eba48b1fd";
        String url = "https://open.weixin.qq.com/connect/qrconnect?"
                + "appid="+appId+""
                + "&redirect_uri="+redirect_url+""
                + "&response_type=code"
                + "&scope=snsapi_login"
                + "&state=dusen";
        response.sendRedirect(url);
    }
    @RequestMapping(value="/weixinLoginCallback")
        public void  weixinLoginCallback(HttpServletRequest request,HttpServletResponse response) throws JSONException,IOException {
            String code =  request.getParameter("code");
            String state =  request.getParameter("state");
            if(code == null || !"dusen".equals(state)){
                ///return null;
            }
            TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl,"/sns/oauth2/access_token"));
            Map<String,String> map=new HashMap<>();
            map.put("appid","wxa614bd4eba48b1fd");
            map.put("secret","4b3e56f7410168f4e357948e8e04f32a");
            map.put("code",code);
            map.put("grant_type","authorization_code");
        JSONObject jsonObject = JSONObject.parseObject(wxClient.get(map));
        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        String refresh_token = jsonObject.getString("refresh_token");
        TemplateClient infoUrl = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl,"/sns/userinfo"));
        Map<String,String> infomap=new HashMap<>();
        infomap.put("access_token",access_token);
        infomap.put("openid",openid);
        JSONObject infoObject = JSONObject.parseObject(infoUrl.get(infomap));
        Result result =lan_api.employeeFindWechat2(infoObject.get("unionid").toString());
        String id="0";
        if(result.getCode() == Result.RESULT_CODE_SUCCESS){
            LinkedHashMap json=(LinkedHashMap)result.getData();
            id=json.get("id").toString();
        }
        String url = "http://127.0.0.1:8080/#/login?"
                + "&id="+id;
        response.sendRedirect(url);

    }


    @GetMapping(value = "/check/unionId")
    public Result checkUnionId(String openid,String unionId){
        Result result = lan_api.employeeFindWechat(openid);
        if(result.getCode() == Result.RESULT_CODE_SUCCESS){
            LinkedHashMap json=(LinkedHashMap)result.getData();
             String mobile= json.get("mobile").toString();
            Result result1 =lan_api.employeeBindWechat(mobile,openid,unionId);;
            return result1;
        }
         return Result.getFailResult("用户未注册");
    }
}

