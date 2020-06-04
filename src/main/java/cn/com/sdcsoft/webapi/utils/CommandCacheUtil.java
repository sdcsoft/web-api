package cn.com.sdcsoft.webapi.utils;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;


@Component
public class CommandCacheUtil extends CacheUtil{

    @Override
    protected Cache getCache() {
        return cacheManager.getCache("commands");
    }

}