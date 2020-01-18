package cn.com.sdcsoft.webapi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class CacheUtil {

    protected String Cache_Name;

    @Autowired
    protected CacheManager cacheManager;

    protected abstract Cache getCache();

    public Object getCacheItem(String key){
        Cache cache = getCache();
        Cache.ValueWrapper element = cache.get(key);

        if (null == element){
            return null;
        }
        return element.get();
    }

    public void putCacheItem(String key, Object value) {
        Cache cache = getCache();
//        Cache.ValueWrapper element = cache.get(key);
//
//        if (null == element) {
//            cache.put(key, value);
//            return;
//        }
        cache.put(key, value);
    }

}
