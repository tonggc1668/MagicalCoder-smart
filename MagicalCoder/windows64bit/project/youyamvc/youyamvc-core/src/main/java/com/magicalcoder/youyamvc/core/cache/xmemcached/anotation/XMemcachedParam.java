package com.magicalcoder.youyamvc.core.cache.xmemcached.anotation;

import java.lang.annotation.*;

/*
	by 何栋宇
	2013-1-30
	自定义注解
 */
@Target(ElementType.METHOD)//放在方法前面的注解 也要 放在 类CLASS 属性FIELD上的
@Retention(RetentionPolicy.RUNTIME)// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented//该注解将被保护在javadoc中
public @interface XMemcachedParam {
	//失效时间 秒单位
	int expire();
	
}
