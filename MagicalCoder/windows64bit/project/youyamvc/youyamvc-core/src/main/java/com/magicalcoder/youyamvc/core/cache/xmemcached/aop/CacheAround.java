package com.magicalcoder.youyamvc.core.cache.xmemcached.aop;

import com.magicalcoder.youyamvc.core.cache.xmemcached.anotation.XMemcachedParam;
import com.magicalcoder.youyamvc.core.cache.xmemcached.utils.MemcachedClientUtils;
import com.magicalcoder.youyamvc.core.common.utils.log.Log4jUtils;
import net.rubyeye.xmemcached.MemcachedClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;


/*
	by 何栋宇
	2013-1-30
	声明：
	1:外部方法testCache设置了 @XMemcachedParam(expire = 3600 ) 发现却没有成功进入around方法：那是因为
	testCache必须通过spring注入到其他service中，service.testCache方可实现arount 虽然垃圾了点；如果任何一个地方
	这么调用testCache();那是不会被around进来的
	2:缓存没法使用了，什么原因 需要把存入缓存的dto implements Serializable 便可解决
 */
@Aspect
public class CacheAround implements InitializingBean {
	
	//凡是方法上有此注解的将被拦截
	@Around("@annotation(com.magicalcoder.youyamvc.core.cache.xmemcached.anotation.XMemcachedParam)")
	public Object around(ProceedingJoinPoint pj) throws Throwable{
		//目标方法名称
		String targetMethodName = pj.getSignature().getName();
		//目标类名称
		String targetClassName = pj.getTarget().getClass().getName();
		//目标方法参数
		Object[] args=pj.getArgs();
//		args.toString();
		//失效时间定义
		int expire=0;
		//是否配置缓存
		boolean  isCacheSet=false;
		//反射获取目标类
		Object c = Class.forName(targetClassName).newInstance();
		//目标类中的所有声明的方法
		Method[] methodArray=c.getClass().getDeclaredMethods();
		//遍历找到当前被aop的方法：找到它的anotation配置的值
		for(int i=0;i<methodArray.length;i++){
			Method method=methodArray[i];
			//如果此方法被配置的@MemcacheParam
			if(method.isAnnotationPresent(XMemcachedParam.class)){
				//类中所有方法匹配到当前执行方法
				if(method.getName().equals(targetMethodName)){
					XMemcachedParam memcacheParam = method.getAnnotation(XMemcachedParam.class);
					expire = memcacheParam.expire();
					isCacheSet=true;
					break;
				}
			
			}
		}
		if(!isCacheSet || expire<=0){//没有设置缓存 设置缓存错误
			return pj.proceed();//直接执行完方法返回
		}else{
			String memcacheKey=MemcachedClientUtils.buildeKey(targetClassName, targetMethodName, args);
			return dealMemcache(pj, memcacheKey, expire);
		}
		
	}
	private Object dealMemcache(ProceedingJoinPoint pj,String memcacheKey,int expire) 
			{
		//构建memcache 的 key值
//		Log4jUtils.getLog(CacheAround.class).error("memcache key:"+memcacheKey);
		//下面的逻辑是在缓存中查询是否存在此key值的缓存
		Object cacheObj=null;
		MemcachedClient client= MemcachedClientUtils.get();
		boolean memcacheException=false;
		try{
			cacheObj = client.get(memcacheKey);
		}catch(Exception e){
			e.printStackTrace();
			Log4jUtils.getLog(CacheAround.class).error("memcache服务器异常...");
			memcacheException=true;
		}
		//是否需要重置缓存值
		if(cacheObj==null){//不在缓存中
			Object returnParam=null;
			//直接调用目标方法
			try {
				returnParam=pj.proceed();
				if(!memcacheException){
                    synchronized(CacheAround.class){
                        if(returnParam==null){//修复memcache不支持set  null
                            client.delete(memcacheKey);
                        }else{
                            //根据 memcacheKey expire来重设缓存
                            client.set(memcacheKey, expire, returnParam);
                        }
                    }
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return returnParam;
		}else{
			//返回缓存内的值
			return cacheObj;
		}
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CacheAround Initializing finished");
    }
}
