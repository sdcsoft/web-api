package cn.com.sdcsoft.webapi.wechat.controller;


import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/wechat/employee")
public class Wechat_EmployeeController {

    @Autowired
    LAN_API lan_api;


    @GetMapping(value = "/getwx")
    public Result getWx(String openid) {
        Result result = lan_api.employeeFindWechat(openid);
        if (result.getCode() > 0){//微信未绑定或其他错误
            return result;
        }
        // TODO 检索微信自己的库，进行相关处理
        // TODO ......
        return result;
    }


}
