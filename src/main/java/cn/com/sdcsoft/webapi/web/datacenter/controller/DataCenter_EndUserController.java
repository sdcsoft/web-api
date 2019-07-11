package cn.com.sdcsoft.webapi.web.datacenter.controller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 终端用户接口
 * 注意：终端用户是一个组织，类似与企业
 */
@RestController
@RequestMapping(value = "/webapi/datacenter/enduser", produces = "application/json;charset=utf-8")
@Auth
public class DataCenter_EndUserController extends BaseController {

    /**
     * 获取终端用户列表
     * @return
     */
    @GetMapping(value = "/list")
    public String getAll() {
        return lan_api.enduserList();
    }

    /**
     * 创建终端用户
     * @param endUserName
     * @param status
     * @return
     */
    @PostMapping(value = "/create")
    public String create(String endUserName, int status) {
        return lan_api.enduserCreate(endUserName,status);
    }

    /**
     * 修改终端用户
     * @param id
     * @param endUserName
     * @param status
     * @return
     */
    @PostMapping(value = "/modify")
    public String modify(int id, String endUserName, int status) {
        return lan_api.enduserModify(id,endUserName,status);
    }


}
