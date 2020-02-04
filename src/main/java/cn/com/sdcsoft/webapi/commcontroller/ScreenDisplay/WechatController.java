package cn.com.sdcsoft.webapi.commcontroller.ScreenDisplay;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/screen/wechat")
public class WechatController {

    @Autowired
    LAN_API lan_api;

    @RequestMapping("/users/amount")
    public Result getWechatUsersAmount(){
        return lan_api.countWechatUsersAmount();
    }

    @RequestMapping("/users")
    public Result getWechatUsers(){
        return lan_api.countWechatUsers();
    }
}
