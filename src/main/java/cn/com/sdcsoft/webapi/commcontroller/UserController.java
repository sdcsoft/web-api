package cn.com.sdcsoft.webapi.commcontroller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/webapi/user", produces = "application/json;charset=utf-8")
@Auth
public class UserController {

    @Autowired
    LAN_API lan_api;

    @PostMapping(value = "/change/password", produces = "application/json;charset=utf-8")
    public String changePassword(String password, HttpServletRequest request) {
        String employeeId = request.getAttribute(CookieService.USER_INFO_FIELD_NAME_EmployeeID).toString();
        return lan_api.employeeChangePassword(employeeId, password);
    }
}
