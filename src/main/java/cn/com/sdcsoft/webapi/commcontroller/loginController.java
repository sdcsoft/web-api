package cn.com.sdcsoft.webapi.commcontroller;

import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/webapi")
public class loginController {

    @Autowired
    private CookieService cookieService;
    @Autowired
    LAN_API lan_api;

    private Result getResult(String loginId, String password, JSONObject employeeJson, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        if (0 == employeeJson.getIntValue("code")) {
            Employee employee = JSONObject.parseObject(employeeJson.getString("data"), Employee.class);
            if (null == employee) {
                return Result.getFailResult("系统中没有该用户！");
            }
            if (0 == employee.getOrgStatus()) {
                return Result.getFailResult("您所在的公司已被禁用，因此无法登录系统，请尽快联系管理员处理！");
            }
            if (0 == employee.getStatus()) {
                return Result.getFailResult("账号已被禁用，请及时联系统管理员处理!");
            }
            if (!employee.getPassword().equals(password)) {
                return Result.getFailResult("用户名或密码错误！");
            }

            //发token
            //记录token与用户的映射关系
            //对接口调用进行身份认证
//            Cookie cookie = cookieService.getUserToken(loginId.toString());
//            response.addCookie(cookie);
            //记录用户信息
            Cookie cookie = cookieService.getUserCookie(employee);
            response.addCookie(cookie);

            return Result.getSuccessResult("登录成功！", employeeJson.getJSONObject("data"));
        } else {
            return Result.getFailResult(employeeJson.getString("msg"));
        }
    }

//    @PostMapping(value = "/login")
//    public Result login(String loginId, String password, HttpServletResponse response) {
//        JSONObject obj = JSONObject.parseObject(lan_api.employeeFind(loginId));
//        obj.getJSONObject("data").put("orgName","测试锅炉厂");
//        obj.getJSONObject("data").put("orgStatus",1);
//        if (0 == obj.getIntValue("code")) {
//            return getResult(loginId,password,obj,response);
//        } else {
//            return Result.getFailResult(obj.getString("msg"));
//        }
//    }



    @PostMapping(value = "/datamanage/login")
    public Result dataManageLogin(String loginId, String password,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject obj = JSONObject.parseObject(lan_api.EmployeeFindCompanyUser(loginId));
        if (0 == obj.getIntValue("code")) {
           return getResult(loginId,password,obj,request,response);
        } else {
            return Result.getFailResult(obj.getString("msg"));
        }
    }



    @PostMapping(value = "/customer/login")
    public Result customerLogin(String loginId, String password, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject obj = JSONObject.parseObject(lan_api.EmployeeFindCustomerUser(loginId));
        if (0 == obj.getIntValue("code")) {
            return  getResult(loginId,password,obj,request,response);
        } else {
            return Result.getFailResult(obj.getString("msg"));
        }
    }

    @PostMapping(value = "/agent/login")
    public Result agentLogin(String loginId, String password, int orgType, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject obj = JSONObject.parseObject(lan_api.EmployeeFindAgentUser(loginId));
        if (0 == obj.getIntValue("code")) {
            return  getResult(loginId,password,obj,request,response);
        } else {
            return Result.getFailResult(obj.getString("msg"));
        }
    }

    @PostMapping(value = "/enduser/login")
    public Result endUserLogin(String loginId, String password, int orgType,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject obj = JSONObject.parseObject(lan_api.employeeFind(loginId));
        if (0 == obj.getIntValue("code")) {
            return getResult(loginId,password,obj,request,response);
        } else {
            return Result.getFailResult(obj.getString("msg"));
        }
    }
}
