package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.wechat.client.TemplateClient;
import feign.Feign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/wechat/Sim")
public class Wechat_SimController {





    @GetMapping(value = "/iMEI")
    public String getSimfindOne(String iMEI) {
        TemplateClient wxClient = Feign.builder().target(TemplateClient.class, String.format("%s%s", "http://simapi.sdcsoft.com.cn","/sim/find/iMEI"));
        Map<String,String> map=new HashMap<>();
        map.put("iMEI",iMEI);
        return wxClient.get(map);
    }

}
