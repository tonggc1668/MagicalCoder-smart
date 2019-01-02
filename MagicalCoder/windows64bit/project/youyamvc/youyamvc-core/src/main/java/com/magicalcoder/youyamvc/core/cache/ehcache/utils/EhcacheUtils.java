package com.magicalcoder.youyamvc.core.cache.ehcache.utils;

import com.alibaba.fastjson.JSON;
import com.magicalcoder.youyamvc.core.cache.ehcache.factory.EhcacheFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * Created by www.magicalcoder.com on 14-5-22.
 * 799374340@qq.com
 */
public class EhcacheUtils {

    //key值由类名 方法名 参数构成
    public static String buildeKey(String targetClassName,String targetMethodName,Object... args){
        StringBuilder cacheKey = new StringBuilder();
        cacheKey.append(targetClassName).append(".").append(targetMethodName).append("(");
        if(args!=null && args.length>0){
            cacheKey.append(JSON.toJSONString(args).replaceAll("\\s","_"));
        }
//        if(cacheKey.length()>254){
//            System.err.println(cacheKey+"  ehcacheKey 长度大于255 错误");
//        }
        return cacheKey.toString()+")";
    }
    //key值由类名 方法名 参数构成
    public static String buildeKey(Class clazz,String targetMethodName,Object... args){
        String targetClassName = clazz.getName();
        StringBuilder cacheKey = new StringBuilder();
        cacheKey.append(targetClassName).append(".").append(targetMethodName).append("(");
        if(args!=null && args.length>0){
            cacheKey.append(JSON.toJSONString(args).replaceAll("\\s","_"));
        }
//        if(cacheKey.length()>254){
//            System.err.println(cacheKey+"  ehcachekey 长度大于255 错误");
//        }
        return cacheKey.toString()+")";
    }

    /**
     *
     * @param ehcacheKey
     * @param timeToIdleSeconds
     * @param timeToLiveSeconds
     * @param newCache
     */
    public static void resetCache(String ehcacheKey,int timeToIdleSeconds,int timeToLiveSeconds,Object newCache){
        Cache cache =  EhcacheFactory.get().getDefaultCache();
        synchronized (EhcacheUtils.class){
            Element newElement = new Element(ehcacheKey,newCache,false,timeToIdleSeconds,timeToLiveSeconds);
            cache.put(newElement);
        }
    }
    /**
     *
     * @param ehcacheKey
     * @param expireSeconds
     * @param newCache
     */
    public static void resetCache(String ehcacheKey,int expireSeconds,Object newCache){
        Cache cache =  EhcacheFactory.get().getDefaultCache();
        synchronized (EhcacheUtils.class){//避免脏写
            Element newElement = new Element(ehcacheKey,newCache,false,expireSeconds,expireSeconds);
            cache.put(newElement);
        }
    }
    /**
     * @param ehcacheKey
     */
    public static Object getCache(String ehcacheKey){
        Cache cache =  EhcacheFactory.get().getDefaultCache();
        Element cacheElement = cache.get(ehcacheKey);
        if(cacheElement!=null){
            return cacheElement.getObjectValue();//缓存值
        }else {
            System.out.println("缓存返回null,可能原始存进去的缓存值就为null");
            return null;
        }
    }
    /**
     * @param ehcacheKey
     */
    public static void removeCache(String ehcacheKey){
        Cache cache =  EhcacheFactory.get().getDefaultCache();
        cache.remove(ehcacheKey);
    }
}
