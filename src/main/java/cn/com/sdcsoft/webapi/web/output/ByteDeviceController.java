package cn.com.sdcsoft.webapi.web.output;

import cn.com.sdcsoft.webapi.commservice.DeviceCacheService;
import cn.com.sdcsoft.webapi.wechat.client.TemplateClient;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/webapi/output/device")
public class ByteDeviceController {

    @Value("${device.cache.online}")
    private String onlinePathUrl;

    @Autowired
    DeviceCacheService deviceCacheService;

    @GetMapping(value = "/get")
    public byte[] getData(String deviceNo) {
        return deviceCacheService.getDeviceCacheData(deviceNo);
    }

    @GetMapping(value = "/online")
    public  String online(){
        TemplateClient client =
                Feign.builder().target(TemplateClient.class, onlinePathUrl);
        return client.get();
    }
}
