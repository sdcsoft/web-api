package cn.com.sdcsoft.webapi.web.datacenter.controller.core;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.web.boilermanage.service.UserService;
import cn.com.sdcsoft.webapi.web.datacenter.entity.OrgType;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 注册用户管理接口
 */
@RestController
@RequestMapping(value = "/webapi/datacenter/core/employee", produces = "application/json;charset=utf-8")
@Auth
public class Core_EmployeeController extends BaseController{

    @Autowired
    UserService boilerUserService;
    /**
     * 获取注册用户列表
     * @return
     */
    @GetMapping(value = "/list")
    public String getAll() {
        return lan_api.employeeList();
    }

    /**
     * 创建用户
     * @param employee
     * @return
     */
    @PostMapping(value = "/create")
    public String create(@RequestBody Employee employee) {
        String str = lan_api.employeeCreate(employee);
        JSONObject obj = JSONObject.parseObject(str);
        if (0 == obj.getIntValue("code")) {
            Employee resultEmployee = JSONObject.parseObject(obj.getString("data"), Employee.class);
            if(resultEmployee.getOrgType() == OrgType.ORG_TYPE_Boiler){
                if(null == employee.getIsAdmin() || employee.getIsAdmin() == 0){
                    boilerUserService.createUser(resultEmployee);
                }
                else {
                    boilerUserService.createAdmin(resultEmployee);
                }
            }
        }
        return str;
    }

    /**
     * 根据注册手机号/邮箱查询用户信息
     * @param loginId
     * @return
     */
    @GetMapping(value = "/find")
    public String findEmployee(String loginId) {
        return lan_api.employeeFind(loginId);
    }

    /**
     * 修改用户信息
     * 说明：核心管理平台专用接口
     * @param employee
     * @return
     */
    @PostMapping(value = "/modify")
    public String modifyEmployee(Employee employee) {
        return lan_api.employeeModify(employee);
    }

    /**
     * 修改用户状态
     * 说明：核心管理平台专用接口
     * @param loginId
     * @param status
     * @return
     */
    @PostMapping(value = "/change/status")
    public String modifyEmployeeStatus(String loginId, int status) {
        return lan_api.employeeChangeStatus(loginId,status);
    }

    /**
     * 修改基本信息
     * 说明：微信或平台用户修改基本信息的接口
     * @param loginId
     * @param mobile
     * @param email
     * @param realName
     * @return
     */
    @PostMapping(value = "/change/infos")
    public String changeEmployeeInfos(String loginId,String mobile, String email, String qq,String realName) {
        return lan_api.employeeChangeInfos(loginId,mobile,email,qq,realName);
    }

    /**
     * 修改密码
     * @param loginId
     * @param password
     * @return
     */
    @PostMapping(value = "/change/user/password")
    public String changeEmployeePassword(String loginId, String password) {
        return lan_api.employeeChangeUserPassword(loginId,password);
    }

}
