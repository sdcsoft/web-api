package cn.com.sdcsoft.webapi.web.output;

import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/decoder")
public class DecoderController {

    @Autowired
    LAN_API lan_api;

    @RequestMapping(value = "/decode", produces = { "application/json;charset=UTF-8" })
    public String getDecode(String deviceNo) {
        return lan_api.deviceFindByDeviceNo(deviceNo);
    }

    @RequestMapping(value = "/suffix", produces = { "application/json;charset=UTF-8" })
    public String getSuffix(@RequestParam("deviceNo") String id) {
        return lan_api.deviceFindSuffix(id);
    }
}
