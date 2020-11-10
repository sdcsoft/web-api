package cn.com.sdcsoft.webapi.web.datacenter.boiler.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import cn.com.sdcsoft.webapi.web.boilermanage.service.UserService;
import cn.com.sdcsoft.webapi.web.entity.OrgType;
import cn.com.sdcsoft.webapi.web.entity.OrgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/webapi/datacenter/boiler/user")
@Auth
public class Boiler_UserController {

    @Autowired
    LAN_API lan_api;

    @Autowired
    UserService userService;

    /**
     * 变更注册用户到锅炉厂用户
     *
     * @param orgUser
     */
//    @PostMapping(value = "/change")
//    public Result change(@RequestBody OrgUser orgUser) {
//        Result result = lan_api.employeeModifyOrg(orgUser.getId(), OrgType.ORG_TYPE_Boiler, orgUser.getOrgId());
//        if (Result.RESULT_CODE_SUCCESS == result.getCode()) {
//            if (null == orgUser.getRoleId() || 0 == orgUser.getRoleId()) {
//                userService.createAdmin(orgUser);
//            } else {
//                userService.createUser2(orgUser);
//            }
//            return Result.getSuccessResult();
//        }
//        return result;
//    }
}
