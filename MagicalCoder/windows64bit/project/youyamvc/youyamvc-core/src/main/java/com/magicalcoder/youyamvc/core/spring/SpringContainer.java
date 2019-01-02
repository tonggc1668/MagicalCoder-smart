package com.magicalcoder.youyamvc.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * qq:799374340
 * @author hdy
 * 2013-7-16上午10:21:47
 */
public class SpringContainer  implements ApplicationContextAware,InitializingBean{

	private static ApplicationContext ctx;
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		//初始化ctx
		setCtx(ctx);
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("-初始化SpringContainer...(如果第二次见到我说明你部署配置不正确，重复加载了工程)");
	}
	public static  <T> T getBean(String beanId, Class<T> clazz){
		if(beanId==null || "".equals(beanId)){
			return null;
		}
		try{
			return ctx.getBean(beanId,clazz);
		}catch(Exception e){
//			e.printStackTrace();
		}
		return null;
	}
    public static<T> T getBean(Class<T> clazz){
        if(clazz==null){
            return null;
        }
        try{
            return ctx.getBean(clazz);
        }catch(Exception e){
//			e.printStackTrace();
        }
        return null;
    }
	public static Object getBean(String beanId){
		if(beanId==null || "".equals(beanId)){
			return null;
		}
		return ctx.getBean(beanId);
	}
	public static ApplicationContext getCtx() {
		return ctx;
	}
	private static void setCtx(ApplicationContext ctx) {
		SpringContainer.ctx = ctx;
	}
	
}
