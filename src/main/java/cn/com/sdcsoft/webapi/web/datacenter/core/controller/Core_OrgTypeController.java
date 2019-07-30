package cn.com.sdcsoft.webapi.web.datacenter.core.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织类型管理接口
 */
@RestController
@RequestMapping(value = "/webapi/datacenter/core/org", produces = "application/json;charset=utf-8")
@Auth
public class Core_OrgTypeController extends BaseController{

    /**
     * 获取组织类型列表
     * @return
     */
    @GetMapping("/list")
    public String getList2() {
       return lan_api.orgList();
 }

    /**
     * 修改组织类型信息，只能修改类型名称
     * @param orgType
     * @param orgTypeName
     * @return
     */
    @PostMapping("/modify")
    public String modify(int orgType, String orgTypeName) {
        return lan_api.orgModify(orgType,orgTypeName);
    }

    /**
     * 创建组织类型
     * @param orgType
     * @param orgTypeName
     * @return
     */
    @PostMapping("/create")
    public String create(int orgType, String orgTypeName) {
        return lan_api.orgCreate(orgType,orgTypeName);
    }


}
