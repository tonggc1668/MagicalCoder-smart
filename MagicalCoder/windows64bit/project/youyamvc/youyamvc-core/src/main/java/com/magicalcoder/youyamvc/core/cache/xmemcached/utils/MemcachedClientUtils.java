package com.magicalcoder.youyamvc.core.cache.xmemcached.utils;

import com.alibaba.fastjson.JSON;
import com.magicalcoder.youyamvc.core.spring.SpringContainer;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.concurrent.TimeoutException;


/*
	by 何栋宇
	2013-1-30
 */
public class MemcachedClientUtils {

	public static MemcachedClient get(){
		return SpringContainer.getBean("memcachedClient", MemcachedClient.class);
	}
    //key值由类名 方法名 参数构成
    public static String buildeKey(String targetClassName,String targetMethodName,Object... args){
        StringBuilder memcacheKey = new StringBuilder();
        memcacheKey.append(targetClassName).append(".").append(targetMethodName).append("(");
        if(args!=null && args.length>0){
            memcacheKey.append(JSON.toJSONString(args).replaceAll("\\s","_"));
        }
        if(memcacheKey.length()>254){
            System.err.println(memcacheKey+"  memcackey 长度大于255 错误");
        }
        return memcacheKey.toString()+")";
    }
    //key值由类名 方法名 参数构成
    public static String buildeKey(Class clazz,String targetMethodName,Object... args){
        String targetClassName = clazz.getName();
        StringBuilder memcacheKey = new StringBuilder();
        memcacheKey.append(targetClassName).append(".").append(targetMethodName).append("(");
        if(args!=null && args.length>0){
            memcacheKey.append(JSON.toJSONString(args).replaceAll("\\s","_"));
        }
        if(memcacheKey.length()>254){
            System.err.println(memcacheKey+"  memcackey 长度大于255 错误");
        }
        return memcacheKey.toString()+")";
    }

    /**
     *
     * @param key 缓存key
     * @param expireSeconds 失效秒 时间太大缓存就会没用，3600×30最大
     * @param newCache 新更新值
     */
	public static void resetCache(String key,int expireSeconds,Object newCache){
        MemcachedClient client = get();
        try {
            synchronized (MemcachedClientUtils.class){
                client.set(key,expireSeconds,newCache);
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }

    public static <T> T get(String key){
        MemcachedClient client = get();
        try {
            return client.get(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void delete(String key){
        MemcachedClient client = get();
        try {
            client.delete(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }
}
