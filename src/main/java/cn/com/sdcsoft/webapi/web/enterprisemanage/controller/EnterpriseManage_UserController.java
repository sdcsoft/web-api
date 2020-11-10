package cn.com.sdcsoft.webapi.web.enterprisemanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_ProductUserMapper;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_ResourceMapper;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_UserMapper;
import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.Role;
import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.User;
import cn.com.sdcsoft.webapi.web.entity.OrgType;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * 企业员工
 */
@RestController
@RequestMapping(value = "/webapi/enterprise/user", produces = "application/json;charset=utf-8")
public class EnterpriseManage_UserController {

    @Autowired
    LAN_API lan_api;
    @Autowired
    private Enterprise_DB_ProductUserMapper productUserMapper;
    @Autowired
    private Enterprise_DB_UserMapper userMapper;

    @Autowired
    private Enterprise_DB_ResourceMapper resourceMapper;

    @Auth
    @GetMapping("/info")
    public Result getUserInfo(Integer employeeId) {
        User user = userMapper.findUserByEmployeeId(employeeId);
        if (null != user) {
            //判断是否为企业管理员
            if (1 == user.getRoleId()) {//企业管理员加载企业资源信息
                user.setListResource(resourceMapper.getOrgResources(user.getOrgId()));
            } else {//非企业管理员，加载用户角色映射信息
                user.setListResource(resourceMapper.getUserResources(employeeId));
            }
            return Result.getSuccessResult(user);
        } else {
            return Result.getFailResult("系统中不存在当前用户信息！");
        }
    }

    @Auth
    @GetMapping("/find")
    public Result findUserInfo(Integer userId) {
        User user = userMapper.findUserById(userId);
        if (null != user) {
            //判断是否为企业管理员
            if (null != user.getRoleId() && 1 == user.getRoleId()) {//企业管理员加载企业资源信息
                return Result.getFailResult("无法对系统管理员进行操作！");
            }
            return Result.getSuccessResult(user);
        } else {
            return Result.getFailResult("系统中不存在当前用户信息！");
        }
    }


    @Auth
    @PostMapping("/resources")
    public Result getUserResources(Integer employeeId) {
        User user = userMapper.findUserByEmployeeId(employeeId);
        if (null != user) {
            ///判断是否为企业管理员
            if (1 == user.getRoleId()) {//企业管理员加载企业资源信息
                return Result.getSuccessResult(resourceMapper.getOrgResources(user.getOrgId()));
            } else {//非企业管理员，加载用户角色映射信息
                return Result.getSuccessResult(resourceMapper.getUserResources(employeeId));
            }
        } else {
            return Result.getFailResult("系统中不存在当前用户信息！");
        }
    }

    /**
     * 获得员工列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Auth
    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        if (null == pageNum || null == pageSize) {
            return Result.getSuccessResult(userMapper.findAll(orgId));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.findAll(orgId);
        PageInfo pageInfo = new PageInfo(list);
        return Result.getSuccessResult(pageInfo);
    }


    /**
     * 编辑员工信息
     *
     * @param user
     * @return
     */
    @Auth
    @PostMapping("/modify")
    public Result modify(@RequestBody User user) {
        userMapper.modifyUser(user);
        return Result.getSuccessResult();
    }

    /**
     * 编辑用户角色
     *
     * @param userId
     * @param role
     * @return
     */
    @Auth
    @PostMapping("/role/modify")
    public Result modifyUserRole(@RequestParam Integer userId, @RequestBody Role role) {
        userMapper.changeUserRole(userId, role.getId(), role.getRoleName());
        return Result.getSuccessResult();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Auth
    @PostMapping(value = "/remove")
    public Result remove(@RequestParam int id, HttpServletRequest request) {
        User user = userMapper.findUserById(id);
        if (user.getRoleId() == Role.SYSTEM_ADMIN_ROLE_ID) {
            return Result.getFailResult("系统管理员不允许删除！");
        }
        Integer employeeId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_EmployeeID).toString());

        if (user.getEmployeeId() == employeeId) {
            return Result.getFailResult("不允许自己删除自己的操作！");
        }
        productUserMapper.clearUserMap(id);
        userMapper.removeUser(id);
        return Result.getSuccessResult();
    }

    /**
     *  生成推荐码
     * @param openId
     * @return
     */
    @Auth
    @RequestMapping("/invcode/create")
    public Result createInvCode(String openId) {
        try{
            User user = userMapper.findUserByOpenId(openId);
            if (null != user) {
                if (null != user.getRoleId() && user.getRoleId() != Role.SYSTEM_ADMIN_ROLE_ID) {
                    return Result.getFailResult("邀请码只允许超级管理员生成！");
                }
                String uuid = UUID.randomUUID().toString().replace("-", "");
                userMapper.createInvCode(uuid,user.getOrgId());
                return Result.getSuccessResult("",uuid);
            } else {
                return Result.getFailResult("系统中不存在该邀请码！");
            }
        }
        catch (Exception ex){
            return Result.getFailResult(ex.getMessage());
        }
    }

    @RequestMapping("/create")
    public Result create(@RequestBody cn.com.sdcsoft.webapi.web.enterprisemanage.entity.User user) {
        try {
            User u = userMapper.findUserByOpenId(user.getInvCode());
            if (null == u) {
                return Result.getFailResult("系统中不存在该邀请码！");
            }

            Employee employee = new Employee();
            employee.setOrgType(OrgType.ORG_TYPE_Enterprise);
            employee.setOrgId(u.getOrgId());
            employee.setPassword(user.getMobile());
            employee.setMobile(user.getMobile());
            employee.setEmail(user.getMobile());
            employee.setStatus(Employee.STATUS_ENABLE);
            employee.setRealName(user.getUserName());
            employee.setUnionId(user.getUnionId());

            String str = lan_api.employeeCreate(employee);
            JSONObject obj = JSONObject.parseObject(str);
            if (Result.RESULT_CODE_SUCCESS == obj.getIntValue("code")) {
                JSONObject data = obj.getJSONObject("data");
                Integer employeeId = data.getInteger("id");
                user.setEmployeeId(employeeId);
                int count = userMapper.createUser(user);
                if (1 == count) {
                    return Result.getSuccessResult(employeeId);
                }
                return Result.getFailResult("用户创建失败！");
            } else {
                return Result.getFailResult("Core用户创建失败！");
            }
        } catch (Exception ex) {
            return Result.getFailResult(ex.getMessage());
        }
    }


}
