package com.magicalcoder.youyamvc.core.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * qq:799374340
 * @author hdy
 * 2013-8-26下午1:32:34 
 */
public class SysPropertiesUtil {
	private static InputStream ios;
	private static Properties p;
	public static String getProperty(final String key){
		if(ios==null){
			ios = SysPropertiesUtil.class.getResourceAsStream("/system.properties");
		}
		if(p==null){
			p = new Properties();
			try {
				p.load(ios);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p.getProperty(key);
	}

    public void clear(){
        ios = null;
        p=null;
    }
}
