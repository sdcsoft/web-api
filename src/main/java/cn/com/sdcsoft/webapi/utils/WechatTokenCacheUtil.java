package cn.com.sdcsoft.webapi.utils;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;


@Component
public class WechatTokenCacheUtil extends CacheUtil{

    @Override
    protected Cache getCache() {
        return cacheManager.getCache("wechat_token");
    }

    public boolean hasKey(String key) {
        Cache cache = getCache();
        Cache.ValueWrapper element = cache.get(key);

        if (null == element)
            return false;

        cache.evict(key);
        return true;
    }

//    public List getKeys() {
//        net.sf.ehcache.Cache cache = (net.sf.ehcache.Cache) getCache();
//        return cache.getKeys();
//    }
}
