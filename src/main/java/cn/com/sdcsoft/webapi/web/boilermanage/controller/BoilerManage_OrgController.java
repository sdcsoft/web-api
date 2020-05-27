package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/webapi/boilermanage/org", produces = "application/json;charset=utf-8")
@Auth
public class BoilerManage_OrgController {

    @Autowired
    LAN_API lan_api;

    @RequestMapping(value = "/prefix")
    public Result getOrgCodePrefix(HttpServletRequest request){
        Integer orgId = Integer.parseInt(request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString());
        try{
            return  Result.getSuccessResult(lan_api.customerPrefix(orgId));
        }
        catch (Exception ex){
            return Result.getFailResult(ex.getMessage());
        }
    }
}
