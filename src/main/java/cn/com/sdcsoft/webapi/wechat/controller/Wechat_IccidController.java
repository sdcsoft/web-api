package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_IccidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wechat/iccid")
public class Wechat_IccidController {

    @Autowired
    Wechat_DB_IccidMapper wechat_db_iccidMapper;

    @GetMapping(value = "/get")
    public String getIccid() {
        return wechat_db_iccidMapper.getIccid().getIccid();
    }
    @GetMapping(value = "/version")
    public String getVersion() {
        return wechat_db_iccidMapper.getIccid().getVersion();
    }
}
