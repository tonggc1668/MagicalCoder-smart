package com.magicalcoder.youyamvc.core.common.utils.log;

import org.apache.log4j.Logger;

/**
 * qq:799374340
 * hdy
 * 2013-6-5上午10:19:10
 */
public class Log4jUtils {
	public static Logger log = Logger.getLogger(Log4jUtils.class);
	public static <T> Logger getLog(Class<T> clazz){
		return Logger.getLogger(clazz);
	}
}
