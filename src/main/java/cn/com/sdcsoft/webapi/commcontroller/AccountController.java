package cn.com.sdcsoft.webapi.commcontroller;

import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_UserMapper;
import cn.com.sdcsoft.webapi.utils.WechatTokenCacheUtil;
import cn.com.sdcsoft.webapi.web.entity.IEmployee;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    static final int ORG_TYPE_Company = 0;
    static final int ORG_TYPE_Enterprise = 1;
    static final int ORG_TYPE_Customer = 2;
    static final int ORG_TYPE_Agent = 3;
    static final int ORG_TYPE_EndUser = 4;


    interface IOrgInfo {
        JSONObject getOrgInfo(int orgId);
    }

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    private CookieService cookieService;
    @Autowired
    LAN_API lan_api;

    @Autowired
    Customer_DB_UserMapper customerUserMapper;


    @Autowired
    WechatTokenCacheUtil cacheUtil;

    private Cookie getUserToken(Employee employee) {
        //发token
        //记录token与用户的映射关系
        //对接口调用进行身份认证
        //Cookie cookie = cookieService.getUserToken(loginId.toString());
        //response.addCookie(cookie);
        //记录用户信息
        Cookie cookie = null;
        try {
            cookie = cookieService.getUserCookie(employee);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    private Result getLoginResult(JSONObject jsonObject, String password, int orgType, IOrgInfo iOrgInfo, HttpServletResponse response) {
        if (0 == jsonObject.getIntValue("code")) {
            Employee employee = JSONObject.parseObject(jsonObject.getString("data"), Employee.class);
            if (null == employee) {
                return Result.getFailResult("用户名或密码错误！.");
            }
            JSONObject orgObj = iOrgInfo.getOrgInfo(employee.getOrgId());
            if (orgType != employee.getOrgType()) {
                return Result.getFailResult("非该系统用户，禁止登录！");
            }
            if (0 == orgObj.getJSONObject("data").getIntValue("status")) {
                return Result.getFailResult("您所在的公司已被禁用，因此无法登录系统，请尽快联系管理员处理！");
            }
            if (0 == employee.getStatus()) {
                return Result.getFailResult("账号已被禁用，请及时联系统管理员处理!");
            }
            if (!employee.getPassword().equals(password)) {
                if (!cacheUtil.hasKey(password)) {
                    return Result.getFailResult("用户名或密码错误！..");
                }
            }
            Cookie cookie = getUserToken(employee);
            if (null != cookie) {
                response.addCookie(cookie);
                return Result.getSuccessResult(employee);
            }
            return Result.getFailResult("无法签发身份认证票，请及时联系统管理员处理！");
        } else {
            return Result.getFailResult(jsonObject.getString("msg"));
        }
    }

    @PostMapping(value = "/datamanage/login")
    public Result dataManageLogin(String loginId, String password, HttpServletResponse response) {
        System.out.println("login id:" + loginId);
        JSONObject obj = JSONObject.parseObject(lan_api.employeeFindCompanyUser(loginId));
        System.out.println(obj);
        return getLoginResult(obj, password, ORG_TYPE_Company,
                (orgId) -> JSON.parseObject("{\"code\":0,\"data\":{\"status\":1}}"),
                response);
    }

    @PostMapping(value = "/enterprise/login")
    public Result enterpriseLogin(String loginId, String password, HttpServletResponse response) {
//        ServiceInstance serviceInstance = loadBalancerClient.choose("LAN-API");
//        System.out.println(String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort()));
        JSONObject obj = JSONObject.parseObject(lan_api.employeeFindEnterprise(loginId));
        return getLoginResult(obj, password, ORG_TYPE_Enterprise, (orgid) -> JSON.parseObject(lan_api.enterpriseFindById(orgid)), response);
    }

    @PostMapping(value = "/customer/login")
    public Result customerLogin(String loginId, String password, HttpServletResponse response) {
        JSONObject obj = JSONObject.parseObject(lan_api.employeeFindCustomerUser(loginId));
        return getLoginResult(obj, password, ORG_TYPE_Customer,
                (orgid) -> JSON.parseObject(lan_api.customerFindById(orgid)),
                response);
    }

    @PostMapping(value = "/agent/login")
    public Result agentLogin(String loginId, String password, HttpServletResponse response) {
        JSONObject obj = JSONObject.parseObject(lan_api.employeeFindAgentUser(loginId));
        return getLoginResult(obj, password, ORG_TYPE_Agent,
                (orgid) -> JSON.parseObject(lan_api.agentFindById(orgid)),
                response);
    }

    @PostMapping(value = "/enduser/login")
    public Result endUserLogin(String loginId, String password, HttpServletResponse response) {
        JSONObject obj = JSONObject.parseObject(lan_api.employeeFind(loginId));
        return getLoginResult(obj, password, ORG_TYPE_EndUser,
                (orgid) -> JSON.parseObject(lan_api.endUserFindById(orgid)),
                response);
    }

    private Result wechatScanBarcodeToLoginPcClient(IEmployee user,HttpServletResponse response){
        if (null == user) {
            return Result.getFailResult("系统中不存在当前用户信息！");
        }
        JSONObject obj = JSONObject.parseObject(lan_api.employeeGet(user.getEmployeeId()));
        Employee employee = JSONObject.parseObject(obj.getString("data"), Employee.class);
        if (null != employee) {
            return getLoginResult(obj, employee.getPassword(), ORG_TYPE_Customer,
                    (orgid) -> JSON.parseObject(lan_api.customerFindById(orgid)),
                    response);
        }
        else{
            return Result.getFailResult("core系统中不存在当前用户信息！");
        }
    }
    /**
     * 微信端锅炉厂管理系统登录
     *
     * @param openId
     * @param response
     * @return
     */
    @PostMapping(value = "/wechat/customer/login")
    public Result wechatScanBarcodeToLoginCustomerPcClient(String openId, HttpServletResponse response) {
        IEmployee user = customerUserMapper.findUserByOpenId(openId);
        return wechatScanBarcodeToLoginPcClient(user,response);
    }

    /**
     * 用户身份识别
     *
     * @param openId 锅炉厂微信用户openid或uuid
     * @return
     */
    @RequestMapping("/wechat/customer/openid")
    public Result findCustomerUserByOpenId(String openId) {
        cn.com.sdcsoft.webapi.web.boilermanage.entity.User user = customerUserMapper.findUserByOpenId(openId);
        if (null != user) {
            return Result.getSuccessResult(user);
        } else {
            return Result.getFailResult("系统中不存在当前用户信息！");
        }
    }

    @Autowired
    Enterprise_DB_UserMapper enterprise_db_userMapper;
    /**
     * 用户身份识别
     *
     * @param openId 锅炉厂微信用户openid或uuid
     * @return
     */
    @RequestMapping("/wechat/enterprise/openid")
    public Result findEnterpriseUserByOpenId(String openId) {
        cn.com.sdcsoft.webapi.web.enterprisemanage.entity.User user = enterprise_db_userMapper.findUserByOpenId(openId);
        if (null != user) {
            return Result.getSuccessResult(user);
        } else {
            return Result.getFailResult("系统中不存在当前用户信息！");
        }
    }

    /**
     * 微信端锅炉厂管理系统登录
     *
     * @param openId
     * @param response
     * @return
     */
    @PostMapping(value = "/wechat/enterprise/login")
    public Result wechatScanBarcodeToLoginEnterprisePcClient(String openId, HttpServletResponse response) {
        IEmployee user = enterprise_db_userMapper.findUserByOpenId(openId);
        return wechatScanBarcodeToLoginPcClient(user,response);
    }

}
