package cn.com.sdcsoft.webapi.commservice;

import cn.com.sdcsoft.webapi.wechat.client.TemplateClient;
import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 访问设备数据缓存统一服务
 */
@Service
public class DeviceCacheService {
    @Value("${device.cache.data}")
    private String deviceCacheDataUrl;

    public byte[] getDeviceCacheData(String deviceNo) {
        TemplateClient deviceInfoClient = Feign.builder().target(TemplateClient.class, deviceCacheDataUrl);
        Map<String, String> map = new HashMap<>(1);
        map.put("id", deviceNo);
        return deviceInfoClient.getBytes(map);
    }
}
