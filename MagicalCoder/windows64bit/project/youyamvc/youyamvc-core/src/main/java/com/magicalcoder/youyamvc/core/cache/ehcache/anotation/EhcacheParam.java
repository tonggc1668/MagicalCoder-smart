package com.magicalcoder.youyamvc.core.cache.ehcache.anotation;

import java.lang.annotation.*;

/**
 *hdy qq:799374340
 *2013-6-3 上午10:47:06
 */
@Target(ElementType.METHOD)//放在方法前面的注解 也要 放在 类CLASS 属性FIELD上的
@Retention(RetentionPolicy.RUNTIME)// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented//该注解将被保护在javadoc中
public @interface EhcacheParam {
	//缓存真正失效时间  min(timeToIdleSeconds,timeToLiveSeconds); 参考ehcache官方文档
	int timeToIdleSeconds();
	int timeToLiveSeconds();
}
