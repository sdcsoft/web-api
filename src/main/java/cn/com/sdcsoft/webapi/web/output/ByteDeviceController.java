package cn.com.sdcsoft.webapi.web.output;

import cn.com.sdcsoft.webapi.commservice.DeviceCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/webapi/output/device")
public class ByteDeviceController {

    @Autowired
    DeviceCacheService deviceCacheService;

    @GetMapping(value = "/get")
    public byte[] getData(String deviceNo) {
        return deviceCacheService.getDeviceCacheData(deviceNo);
    }
}
