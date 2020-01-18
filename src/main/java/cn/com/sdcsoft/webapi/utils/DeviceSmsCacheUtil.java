package cn.com.sdcsoft.webapi.utils;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;


@Component
public class DeviceSmsCacheUtil extends CacheUtil{

    @Override
    protected Cache getCache() {
        return cacheManager.getCache("device_sms");
    }

}
