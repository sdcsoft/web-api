package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_WechatUserMapper;
import cn.com.sdcsoft.webapi.wechat.entity.WechatUser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/wechat/user")
public class Wechat_UserController {

    private static final String SmsCode = "smsCode";
    private static final String MobileNumber = "MobileNumber";

    @Autowired
    LAN_API lan_api;

    @Autowired
    private Wechat_DB_WechatUserMapper wechat_db_wechatUserMapper;

    private Result getSmsSendResult(Result result, HttpServletRequest request) {
        if (result.getCode() == Result.RESULT_CODE_SUCCESS) {
            HttpSession session = request.getSession(true);
            session.setAttribute(SmsCode, result.getData());
        }
        return result;
    }

    @GetMapping(value = "/reg/sms/zh")
    public Result sendRegSmsZh(String number, HttpServletRequest request) {
        String[] msg = {number,String.format("%s",(int)((Math.random()*9+1)*100000))};
        Result result = lan_api.smsSendVcode("ZH",msg);
        result.setData(msg[1]);
        return getSmsSendResult(result, request);
    }

    @GetMapping(value = "/reg/sms/en")
    public Result sendRegSmsEn(String number, HttpServletRequest request) {
        String[] msg = {number,String.format("%s",(int)((Math.random()*9+1)*100000))};
        Result result = lan_api.smsSendVcode("EN",msg);
        result.setData(msg[1]);
        return getSmsSendResult(result, request);
    }

    /**
     * 正式版微信注册用户
     *
     * @param realName
     * @param openid
     * @return
     */
    @GetMapping(value = "/saveEmployee")
    public Result saveEmployee(String realName, String openid) {
        Employee employee = new Employee();
        employee.setMobile(openid);
        employee.setEmail(openid);
        employee.setRealName(realName);
        employee.setOrgId(0);
        employee.setOrgType(5);
        employee.setStatus(1);
        employee.setWeiXin(openid);
        Result result = JSONObject.parseObject(lan_api.employeeCreate(employee), Result.class);
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(openid);
        wechatUser.setRealName(realName);
        Timestamp d = new Timestamp(System.currentTimeMillis());
        wechatUser.setCreateDatetime(d);
        wechat_db_wechatUserMapper.insertWechatUser(wechatUser);
        return result;
    }
    /**
     * 展会版微信注册用户
     *
     * @param realName
     * @param openid
     * @return
     */
    @GetMapping(value = "/wxShow/saveEmployee")
    public Result saveWxShowEmployee(String realName,String openid){
        WechatUser wechatUser=new WechatUser();
        wechatUser.setOpenId(openid);
        wechatUser.setRealName(realName);
        Timestamp d = new Timestamp(System.currentTimeMillis());
        wechatUser.setCreateDatetime(d);
        if(wechat_db_wechatUserMapper.insertWechatUser(wechatUser)>0){
            return Result.getSuccessResult();
        }
        return Result.getFailResult("添加失败");
    }
    @PostMapping(value = "/wxShow/check/openId")
    public Result checkopenId(String openId){
       List<WechatUser> list= wechat_db_wechatUserMapper.getWechatUserListByopenId(openId);
            if(list.size()>0){
                return Result.getSuccessResult();
            }else{
                return Result.getFailResult("该用户未注册");
            }
    }
    /**
     * 获取绑定微信到已注册账号的短信(中文版)
     *
     * @param mobileNumber 已注册账号手机号
     * @return
     */
    @GetMapping(value = "/bind/sms/zh")
    public Result sendBindMsgZh(String mobileNumber, HttpServletRequest request) {
        Result result = JSONObject.parseObject(lan_api.employeeFind(mobileNumber), Result.class);
        if (result.getCode() == Result.RESULT_CODE_SUCCESS) {//账号存在，发送短信
            String[] msg = {mobileNumber,String.format("%s",(int)((Math.random()*9+1)*100000))};

            result = lan_api.smsSendVcode("ZH",msg);
            result.setData(msg[1]);
            return getSmsSendResult(result, request);
        }
        return result;
    }

    /**
     * 获取绑定微信的短信(国际版)
     *
     * @param mobileNumber 已注册账号手机号
     * @return
     */
    @GetMapping(value = "/bind/sms/en")
    public Result sendBindMsgEn(String mobileNumber, HttpServletRequest request) {
        Result result = JSONObject.parseObject(lan_api.employeeFind(mobileNumber), Result.class);
        if (result.getCode() == Result.RESULT_CODE_SUCCESS) {//账号存在，发送短信
            String[] msg = {mobileNumber,String.format("%s",(int)((Math.random()*9+1)*100000))};

            result = lan_api.smsSendVcode("EN",msg);
            result.setData(msg[1]);
            return getSmsSendResult(result, request);
        }
        return result;
    }

    /**
     * 绑定微信号
     *
     * @param mobileNumber
     * @param openId
     * @return
     */
    @GetMapping(value = "/bind/wechat")
    public Result bindWechat(String mobileNumber, String openId, String unionId) {
        Result result = lan_api.employeeBindWechat(mobileNumber, openId, unionId);
        return result;
    }

    /**
     * 根据openId查询所有用户
     *
     * @param openId
     * @return
     */
    @GetMapping(value = "/find/openId")
    public Result bindWechat(String openId) {
        Result result = lan_api.findWeChatEmployee(openId);
        return result;
    }
}
