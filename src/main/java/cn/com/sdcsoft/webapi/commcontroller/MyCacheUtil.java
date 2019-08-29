package cn.com.sdcsoft.webapi.commcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyCacheUtil {

    private String Cache_Name = "wechat_token";

    @Autowired
    private CacheManager cacheManager;

    private Cache getCache() {
        return cacheManager.getCache(Cache_Name);
    }

    public boolean hasKey(String key) {
        Cache cache = getCache();
        Cache.ValueWrapper element = cache.get(key);

        if (null == element)
            return false;

        cache.evict(key);
        return true;
    }



    public void putData(String key, String value) {
        Cache cache = getCache();
        Cache.ValueWrapper element = cache.get(key);

        if (null == element){
            getCache().put(key, value);
        }
    }

    public List getKeys() {
        net.sf.ehcache.Cache cache = (net.sf.ehcache.Cache) getCache();
        return cache.getKeys();
    }
}
