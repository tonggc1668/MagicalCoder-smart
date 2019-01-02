package com.magicalcoder.youyamvc.core.cache.common;

import com.magicalcoder.youyamvc.core.cache.ehcache.utils.EhcacheUtils;
import com.magicalcoder.youyamvc.core.cache.xmemcached.utils.MemcachedClientUtils;
import com.magicalcoder.youyamvc.core.common.utils.SysPropertiesUtil;

/**
 * Created by hzhedongyu on 2016/3/25.
 */
public class CacheUtil {

    public static void resetCache(String key,int expireSeconds,Object newCache){
        CacheEnum cacheEnum = cacheEnum();
        switch (cacheEnum){
            case MEMCACHE:
                MemcachedClientUtils.resetCache(key,expireSeconds,newCache);
                break;
            case EHCACHE:
                EhcacheUtils.resetCache(key,expireSeconds,newCache);
                break;
            default:
                throw new RuntimeException("未找到缓存类型，请检查system.properties CACHE_TYPE:memcache ehcache");
        }
    }

    public static <T> T get(String key){
        CacheEnum cacheEnum = cacheEnum();
        switch (cacheEnum){
            case MEMCACHE:
                return MemcachedClientUtils.get(key);
            case EHCACHE:
                return (T)EhcacheUtils.getCache(key);
            default:
                throw new RuntimeException("未找到缓存类型，请检查system.properties CACHE_TYPE:memcache ehcache");
        }
    }

    public static void delete(String key){
        CacheEnum cacheEnum = cacheEnum();
        switch (cacheEnum){
            case MEMCACHE:
                MemcachedClientUtils.delete(key);break;
            case EHCACHE:
                EhcacheUtils.removeCache(key);break;
            default:
                throw new RuntimeException("未找到缓存类型，请检查system.properties CACHE_USE:memcache ehcache");
        }
    }

    private static CacheEnum cacheEnum(){
        String cacheType = SysPropertiesUtil.getProperty("CACHE_USE");
        if("memcache".equals(cacheType)){
            return CacheEnum.MEMCACHE;
        }
        return CacheEnum.EHCACHE;
    }

}
