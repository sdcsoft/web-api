package cn.com.sdcsoft.webapi.commcontroller;


import cn.com.sdcsoft.webapi.entity.Result;
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
@RequestMapping(value = "/wechat")
public class WeChatController {



    @Value("${wechat.wx-openid}")
    private String wxOpenIdUrl;

    @Autowired
    LAN_API lan_api;

    @Autowired
    MyCacheUtil cacheUtil;

    @GetMapping(value="/login")
    public void goWeixinAuth(HttpServletResponse response,String url) throws IOException {
        String redirect_url = "http://kuaixin.picp.net:14335/wechat/callback?url="+url;
        String appId ="wxa614bd4eba48b1fd";
        String responseUrl = "https://open.weixin.qq.com/connect/qrconnect?"
                + "appid="+appId+""
                + "&redirect_uri="+redirect_url+""
                + "&response_type=code"
                + "&scope=snsapi_login"
                + "&state=dusen";
        response.sendRedirect(responseUrl);
    }

    @GetMapping(value="/callback")
        public void  weixinLoginCallback(HttpServletRequest request,HttpServletResponse response,String code,String state,String url) throws JSONException,IOException {
            if(code == null || !"dusen".equals(state)){
                return;
            }
            TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl,"/sns/oauth2/access_token"));
            Map<String,String> map=new HashMap<>();
            map.put("appid","wxa614bd4eba48b1fd");
            map.put("secret","4b3e56f7410168f4e357948e8e04f32a");
            map.put("code",code);
            map.put("grant_type","authorization_code");
            JSONObject jsonObject = JSONObject.parseObject(wxClient.get(map));
            String access_token = jsonObject.getString("access_token");
            cacheUtil.putData(access_token,null);
            String openid = jsonObject.getString("openid");
            TemplateClient infoUrl = Feign.builder().target(TemplateClient.class, String.format("%s%s", wxOpenIdUrl,"/sns/userinfo"));
            Map<String,String> infomap=new HashMap<>();
            infomap.put("access_token",access_token);
            infomap.put("openid",openid);
            JSONObject infoObject = JSONObject.parseObject(infoUrl.get(infomap));
            Result result =lan_api.employeeFindWechat2(infoObject.get("unionid").toString());
            String mobile="0";
            if(result.getCode() == Result.RESULT_CODE_SUCCESS){
                LinkedHashMap json=(LinkedHashMap)result.getData();
                mobile=json.get("mobile").toString();
            }
            String responseUrl = String.format(url+"?mobile=%s&token=%s",mobile,access_token);
            response.sendRedirect(responseUrl);
    }
    @PostMapping(value = "/check/unionId")
    public Result checkUnionId(String openId,String unionId){
        Result result = lan_api.employeeFindWechat(openId);
        if(result.getCode() == Result.RESULT_CODE_SUCCESS){
            LinkedHashMap json=(LinkedHashMap)result.getData();
             String mobile= json.get("mobile").toString();
             result =lan_api.employeeBindWechat(mobile,openId,unionId);
            return result;
        }
         return Result.getFailResult("用户未注册");
    }
    @PostMapping(value = "/check/openId")
    public Result checkopenId(String openId){
        Result result = lan_api.employeeFindWechat(openId);
        if(result.getCode() == Result.RESULT_CODE_SUCCESS){
            LinkedHashMap json=(LinkedHashMap)result.getData();
            Object value = json.get("unionId");
            if(value==null||value.toString().equals("")){
                return Result.getFailResult("unionId未绑定");
            }else{
                return Result.getSuccessResult();
            }
        }
        return Result.getFailResult("用户未注册");
    }
}

