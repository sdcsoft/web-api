package cn.com.sdcsoft.webapi.web.endusermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_ResourceMapper;
import cn.com.sdcsoft.webapi.mapper.EndUser_DB.EndUser_DB_UserMapper;
import cn.com.sdcsoft.webapi.web.endusermanage.entity.Role;
import cn.com.sdcsoft.webapi.web.endusermanage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源
 */
@RestController
@RequestMapping(value = "/webapi/endusermanage/resource", produces = "application/json;charset=utf-8")
@Auth
public class EndUserManage_ResourceController {
    @Autowired
    private EndUser_DB_ResourceMapper resourceMapper;
    @Autowired
    private EndUser_DB_UserMapper userMapper;
    /**
     * 通过角色Id获得角色资源列表
     * @param employeeId
     * @return
     */
    @GetMapping(value = "/user")
    public Result getUserResources(Integer employeeId) {
        User user = userMapper.findUserByEmployeeId(employeeId);
        if(null != user){
            if(Role.SYSTEM_ADMIN_ROLE_ID == user.getRoleId()){
                return Result.getSuccessResult(resourceMapper.getOrgResources(user.getOrgId()));
            }
            else{
               return Result.getSuccessResult(resourceMapper.getUserResources(employeeId));
            }
        }
        return Result.getFailResult("系统中不存在请求的用户及资源！");
    }

    /**
     * 通过角色Id获得角色资源列表
     * @param roleId
     * @return
     */
    @GetMapping(value = "/role")
    public Result getRoleResources(Integer roleId) {
        return Result.getSuccessResult(resourceMapper.getRoleResources(roleId));
    }
}
