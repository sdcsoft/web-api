package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.Role;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ResourceMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.User;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ProductUserMapper;

import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 企业员工
 */
@RestController
@RequestMapping(value = "/webapi/boilermanage/user", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_UserController {

    @Autowired
    private ProductUserMapper productUserMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @GetMapping("/info")
    public Result getUserInfo(Integer employeeId){
        User user = userMapper.findUserByEmployeeId(employeeId);
        if(null != user){
            //判断是否为企业管理员
            if(1 == user.getRoleId()){//企业管理员加载企业资源信息
                user.setListResource(resourceMapper.getOrgResources(user.getOrgId()));
            }
            else{//非企业管理员，加载用户角色映射信息
                user.setListResource(resourceMapper.getUserResources(employeeId));
            }
            return Result.getSuccessResult(user);
        }
        else{
            return Result.getFailResult("系统中不存在当前用户信息！");
        }
    }

    @PostMapping("/resources")
    public Result getUserResources(Integer employeeId){
        User user = userMapper.findUserByEmployeeId(employeeId);
        if(null != user){
            ///判断是否为企业管理员
            if(1 == user.getRoleId()){//企业管理员加载企业资源信息
                return Result.getSuccessResult(resourceMapper.getOrgResources(user.getOrgId()));
            }
            else{//非企业管理员，加载用户角色映射信息
                return Result.getSuccessResult(resourceMapper.getUserResources(employeeId));
            }
        }
        else{
            return Result.getFailResult("系统中不存在当前用户信息！");
        }
    }

    /**
     * 获得员工列表
     *
     * @param orgId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result list(int orgId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.findAll(orgId);
        PageInfo pageInfo = new PageInfo(list);
        return Result.getSuccessResult(pageInfo);
    }



    /**
     * 编辑用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/modify")
    public Result modify(@RequestBody User user) {
        userMapper.modifyUser(user);
        return Result.getSuccessResult();
    }

    /**
     * 编辑用户角色
     * @param userId
     * @param role
     * @return
     */
    @PostMapping("/role/modify")
    public Result modifyUserRole(@RequestParam Integer userId,@RequestBody Role role) {
        userMapper.changeUserRole(userId,role.getId(),role.getRoleName());
        return Result.getSuccessResult();
    }
    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/remove")
    public Result remove(@RequestParam int id, HttpServletRequest request) {
        User user = userMapper.findUserById(id);
        if(user.getRoleId() == Role.SYSTEM_ADMIN_ROLE_ID){
            return Result.getFailResult("系统管理员不允许删除！");
        }
        Integer employeeId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_EmployeeID).toString());

        if(user.getEmployeeId() == employeeId){
            return Result.getFailResult("不允许自己删除自己的操作！");
        }
        productUserMapper.clearUserMap(id);
        userMapper.removeUser(id);
        return Result.getSuccessResult();
    }

}
