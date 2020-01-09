package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_ResourceMapper;
import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_UserMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.Role;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.User;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 资源
 */
@RestController
@RequestMapping(value = "/webapi/boilermanage/resource", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_ResourceController {
    @Autowired
    private Customer_DB_ResourceMapper resourceMapper;
    @Autowired
    private Customer_DB_UserMapper userMapper;

    /**
     * 通过角色Id获得角色资源列表
     *
     * @param employeeId
     * @return
     */
    @GetMapping(value = "/user")
    public Result getUserResources(Integer employeeId) {
        User user = userMapper.findUserByEmployeeId(employeeId);
        if (null != user) {
            if (Role.SYSTEM_ADMIN_ROLE_ID == user.getRoleId()) {
                return Result.getSuccessResult(resourceMapper.getOrgResources(user.getOrgId()));
            } else {
                return Result.getSuccessResult(resourceMapper.getUserResources(employeeId));
            }
        }
        return Result.getFailResult("系统中不存在请求的用户及资源！");
    }

    /**
     * 通过角色Id获得角色资源列表
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/role")
    public Result getRoleResources(Integer roleId) {
        return Result.getSuccessResult(resourceMapper.getRoleResources(roleId));
    }
}
