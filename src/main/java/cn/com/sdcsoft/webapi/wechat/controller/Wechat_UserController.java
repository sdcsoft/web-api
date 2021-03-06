package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping(value = "/wechat/user")
public class Wechat_UserController {

    private static final String SmsCode = "smsCode";
    private static final String MobileNumber = "MobileNumber";

    @Autowired
    LAN_API lan_api;

    private Result getSmsSendResult(Result result,HttpServletRequest request) {
        if (result.getCode() == Result.RESULT_CODE_SUCCESS) {
            HttpSession session = request.getSession(true);
            session.setAttribute(SmsCode, result.getData());
        }
        return result;
    }

    @GetMapping(value = "/reg/sms/zh")
    public Result sendRegSmsZh(String number, HttpServletRequest request) {
        Result result = lan_api.smsSendZh(number);
        return getSmsSendResult(result,request);
    }

    @GetMapping(value = "/reg/sms/en")
    public Result sendRegSmsEn(String number, HttpServletRequest request) {
        Result result = lan_api.smsSendEn(number);
        return getSmsSendResult(result,request);
    }


    /**
     * 微信注册用户
     *
     * @param realName
     * @param openid
     * @return
     */
    @GetMapping(value = "/saveEmployee")
    public Result saveEmployee(String realName,String openid){
        Employee employee = new Employee();
        employee.setMobile(openid);
        employee.setEmail(openid);
        employee.setRealName(realName);
        employee.setOrgId(0);
        employee.setOrgType(5);
        employee.setStatus(1);
        employee.setWeiXin(openid);
        Result result = JSONObject.parseObject(lan_api.employeeCreate(employee), Result.class);
        return result;
    }

    /**
     * 获取绑定微信到已注册账号的短信(中文版)
     * @param mobileNumber 已注册账号手机号
     * @return
     */
    @GetMapping(value = "/bind/sms/zh")
    public Result sendBindMsgZh(String mobileNumber,HttpServletRequest request){
        Result result = JSONObject.parseObject(lan_api.employeeFind(mobileNumber),Result.class);
        if(result.getCode() == Result.RESULT_CODE_SUCCESS){//账号存在，发送短信
            result = lan_api.smsSendZh(mobileNumber);
            return getSmsSendResult(result,request);
        }
        return result;
    }

    /**
     * 获取绑定微信的短信(国际版)
     * @param mobileNumber 已注册账号手机号
     * @return
     */
    @GetMapping(value = "/bind/sms/en")
    public Result sendBindMsgEn(String mobileNumber,HttpServletRequest request){
        Result result = JSONObject.parseObject(lan_api.employeeFind(mobileNumber),Result.class);
        if(result.getCode() == Result.RESULT_CODE_SUCCESS){//账号存在，发送短信
            result = lan_api.smsSendEn(mobileNumber);
            return getSmsSendResult(result,request);
        }
        return result;
    }

    /**
     * 绑定微信号
     * @param mobileNumber
     * @param openId
     * @return
     */
    @GetMapping(value = "/bind/wechat")
    public Result bindWechat(String mobileNumber,String openId,String unionId){
        Result result = lan_api.employeeBindWechat(mobileNumber,openId,unionId);
        return result;
    }
}
