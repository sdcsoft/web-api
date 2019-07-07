package cn.com.sdcsoft.webapi.web.datacenter;

import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    LAN_API lan_api;
}
