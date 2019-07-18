package cn.com.sdcsoft.webapi.web.datacenter.controller.boiler;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/webapi/datacenter/boiler/user")
public class Boiler_User {

    /**
     * 变更注册用户到锅炉厂用户
     * @param employeeId 注册用户Id（非登录账号）
     * @param orgId 锅炉厂Id
     * @param isAdmin 是否为管理员 0或null 为管理员
     */
    @PostMapping(value = "/change")
    public void change(@RequestParam Integer employeeId,@RequestParam Integer orgId,@RequestParam Integer isAdmin){

    }
}
