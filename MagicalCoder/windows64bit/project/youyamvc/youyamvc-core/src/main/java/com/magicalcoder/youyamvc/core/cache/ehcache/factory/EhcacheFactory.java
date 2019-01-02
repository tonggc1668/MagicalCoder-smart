package com.magicalcoder.youyamvc.core.cache.ehcache.factory;

import com.magicalcoder.youyamvc.core.cache.ehcache.constant.EhcacheConstant;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.utils.SysPropertiesUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import java.util.HashMap;
import java.util.Map;

/** Ehcache工厂 用来获取配置cache
 *hdy qq:799374340
 *2013-6-3 上午10:59:52
 */
public class EhcacheFactory {
	private  static EhcacheFactory instance = new EhcacheFactory();
	public static EhcacheFactory get(){
		return instance;
	}
	private EhcacheFactory(){
		//禁止外部new
		manager = CacheManager.create();
	}
	private static  CacheManager manager;
	private static Map<String,Cache> cacheMap = new HashMap<String,Cache>();
	//获取配置缓存 并存放cacheMap中
	public Cache getCache(String cacheConfigName){
		Cache cache = cacheMap.get(cacheConfigName);
		if(cache==null){
			cacheMap.put(cacheConfigName, manager.getCache(cacheConfigName));
		}
		return cacheMap.get(cacheConfigName);
	}
    public Cache getDefaultCache(){
        String cacheConfigName = SysPropertiesUtil.getProperty("EHCACHE_TYPE");
        Cache cache = cacheMap.get(cacheConfigName);
        if(cache==null){
            cacheMap.put(cacheConfigName, manager.getCache(cacheConfigName));
        }
        return cacheMap.get(cacheConfigName);
    }
	public void shutDownEhcache(){
		manager.shutdown();
	}

}
