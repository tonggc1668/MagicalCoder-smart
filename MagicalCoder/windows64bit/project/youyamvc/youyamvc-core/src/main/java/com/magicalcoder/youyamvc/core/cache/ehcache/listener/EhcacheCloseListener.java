package com.magicalcoder.youyamvc.core.cache.ehcache.listener;

import com.magicalcoder.youyamvc.core.cache.ehcache.factory.EhcacheFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * qq:799374340
 * hdy
 * 2013-6-5下午1:52:35
 */
public class EhcacheCloseListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent servletcontextevent) {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletcontextevent) {
		//关闭ehcache
		EhcacheFactory.get().shutDownEhcache();
	}
}
