package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.Role;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.RoleResource;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ResourceMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 角色管理
 */
@RestController
@RequestMapping(value = "/webapi/boilermanage/role", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_RoleController {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @GetMapping("/list")
    public Result list(Integer pageNum,Integer pageSize, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Role> list = roleMapper.list(orgId);
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return Result.getSuccessResult(pageInfo);
    }

    /**
     * 创建角色
     *
     * @param role
     * @return
     */
    @PostMapping("/create")
    public Result getUserResources(@RequestBody Role role, HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        role.setOrgId(orgId);
        if(roleMapper.checkExist(role)>0){
            return Result.getFailResult("该数据已存在！");
        }
        roleMapper.createRole(role);
        return Result.getSuccessResult();
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @return
     */
    @PostMapping("/modify")
    public Result modify(@RequestBody Role role,HttpServletRequest request) {
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        role.setOrgId(orgId);
        if(roleMapper.checkExist(role)>0){
            roleMapper.modifyRoleExtendsInfo(role);
            return Result.getSuccessResult();
        }
        roleMapper.modifyRole(role);
        return Result.getSuccessResult();
    }


    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @PostMapping(value = "/remove")
    public Result remove(@RequestParam Integer roleId,HttpServletRequest request) {
        if (0 < roleMapper.checkUsersInRole(roleId)) {
            return Result.getFailResult("因为该数据正在被使用，所以不能删除该数据！");
        }
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());

        resourceMapper.clearRoleResources(roleId);
        roleMapper.removeRole(orgId, roleId);
        return Result.getSuccessResult();
    }

    /**
     * 添加角色资源映射
     *
     * @param roleResources
     * @return
     */
    @PostMapping("/resource/map")
    public Result map(Integer roleId, @RequestBody List<RoleResource> roleResources) {
        roleMapper.clearRoleResourceMap(roleId);
        if(roleResources.size()>0) {
            roleMapper.createRoleResourceMap(roleResources);
        }
        return Result.getSuccessResult();
    }
}