package com.magicalcoder.youyamvc.core.cache.ehcache.aop;

import com.magicalcoder.youyamvc.core.cache.ehcache.anotation.EhcacheParam;
import com.magicalcoder.youyamvc.core.cache.ehcache.constant.EhcacheConstant;
import com.magicalcoder.youyamvc.core.cache.ehcache.factory.EhcacheFactory;
import com.magicalcoder.youyamvc.core.cache.ehcache.utils.EhcacheUtils;
import com.magicalcoder.youyamvc.core.common.utils.SysPropertiesUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;


/*
	by 何栋宇
	2013-1-30
 */
@Aspect
public class EhcacheAround {
	//凡是方法上有此注解的将被拦截
	@Around("@annotation(com.magicalcoder.youyamvc.core.cache.ehcache.anotation.EhcacheParam)")
	public Object around(ProceedingJoinPoint pj) throws Throwable{
		//目标方法名称
		String targetMethodName = pj.getSignature().getName();
		//目标类名称
		String targetClassName = pj.getTarget().getClass().getName();
		//目标方法参数
		Object[] args=pj.getArgs();
		//失效时间定义
		int timeToIdleSeconds=-1;//间隔时间
		int timeToLiveSeconds=-1;//生存时间
		//是否配置缓存
		boolean  isCacheSet=false;
		//反射获取目标类
		Object c = Class.forName(targetClassName).newInstance();
		//目标类中的所有声明的方法
		Method[] methodArray=c.getClass().getDeclaredMethods();
		//遍历找到当前被aop的方法：找到它的anotation配置的值
		for(int i=0;i<methodArray.length;i++){
			Method method=methodArray[i];
			//如果此方法被配置的@EhcacheParam
			if(method.isAnnotationPresent(EhcacheParam.class)){
				//类中所有方法匹配到当前执行方法
				if(method.getName().equals(targetMethodName)){
					EhcacheParam ehcacheParam = method.getAnnotation(EhcacheParam.class);
					timeToIdleSeconds = ehcacheParam.timeToIdleSeconds();
					timeToLiveSeconds = ehcacheParam.timeToLiveSeconds();
					isCacheSet=true;
					break;
				}
			
			}
		}
		//XXX XXX XXX
		if(!isCacheSet || timeToLiveSeconds<0 || timeToLiveSeconds<0){//没有设置缓存 设置缓存错误
			return pj.proceed();//直接执行完方法返回
		}else{
			String ehcacheKey=buildeKey(targetClassName,targetMethodName,args);
			return dealEhcache(pj, ehcacheKey, timeToIdleSeconds, timeToLiveSeconds);
		}
	}
	private Object dealEhcache(ProceedingJoinPoint pj,String ehcacheKey, int timeToIdleSeconds, int timeToLiveSeconds) 
			{
		//下面的逻辑是在缓存中查询是否存在此key值的缓存
		Object cacheObj=null;
		String cacheConfigName = SysPropertiesUtil.getProperty("EHCACHE_TYPE");
		Cache defaultCache = EhcacheFactory.get().getCache(cacheConfigName);//使用配置默认的缓存
		synchronized (EhcacheAround.class) {//解决多并发时缓存某些线程无效
			Element cacheElement = defaultCache.get(ehcacheKey);
			if(cacheElement!=null){
				cacheObj = cacheElement.getObjectValue();//缓存值
			}
			//是否需要重置缓存值
			if(cacheObj==null){//不在缓存中
				Object returnParam=null;
				//直接调用目标方法
				try {
					returnParam=pj.proceed();
//					Log4jUtils.getLog(EhcacheAround.class).info("found NO cache from ehcache key=["+ehcacheKey+"] timeToIdleSeconds=["+timeToIdleSeconds+"s]"+"timeToLiveSeconds=["+timeToLiveSeconds+"s]");
					//根据 配置参数来重设缓存
					Element newElement = new Element(ehcacheKey,returnParam,false,timeToIdleSeconds,timeToLiveSeconds);
					defaultCache.put(newElement);
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return returnParam;
			}else{
				//返回缓存内的值
//				Log4jUtils.getLog(EhcacheAround.class).info("found cache from ehcache key=["+ehcacheKey+"] timeToIdleSeconds=["+timeToIdleSeconds+"s]"+"timeToLiveSeconds=["+timeToLiveSeconds+"s]");
				return cacheObj;
			}
		}
	}

    //key值由类名 方法名 参数构成
    public static String buildeKey(String targetClassName,String targetMethodName,Object... args){
        return EhcacheUtils.buildeKey(targetClassName,targetMethodName,args);
    }
}
