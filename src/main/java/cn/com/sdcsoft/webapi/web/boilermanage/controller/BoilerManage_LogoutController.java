package cn.com.sdcsoft.webapi.web.boilermanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

//import net.unicon.cas.client.configuration.EnableCasClient;

//@EnableCasClient
@Controller
@RequestMapping(value = "/webapi/boilermanage/logout", produces = "application/json;charset=utf-8")
public class BoilerManage_LogoutController {
    @GetMapping("/logoutDefault")
    public String logout1(HttpSession session) {
        session.invalidate();

        // 直接退出，走默认退出方式
        return "redirect:http://server.cas.com:8080/cas/logout";
    }

    @GetMapping("/out")
    public String logout2(HttpSession session) {
        session.invalidate();
        // 退出登录后，跳转到退出成功的页面，不走默认页面
        return "redirect:http://server.cas.com:8080/cas/logout?service=http://localhost:8091/logout/success";
    }

    @GetMapping("/success")
    public String logout2() {
        return "/index";
    }
}
