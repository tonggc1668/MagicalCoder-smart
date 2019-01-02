package com.magicalcoder.youyamvc.core.common.utils;
/**
 * qq:799374340
 * @author hdy
 * 2013-6-25下午1:39:28
 */
public class StringUtils {
	public static boolean isBlank(String str){
		if(str==null || "".equals(str)){
			return true;
		}
		return false;
	}
	public static boolean isNotBlank(String str){
		if(str!=null && !"".equals(str.trim())){
			return true;
		}
		return false;
	}
    public static String deleteLastChar(String str){
        if(isBlank(str)) return str;
        return str.substring(0,str.length()-1);
    }

    public static String deleteLastChar(String str,int len){
        if(isBlank(str)) return str;
        return str.substring(0,str.length()-len);
    }
    public static String deleteBeforeChar(String str,int len){
        if(isBlank(str)) return str;
        return str.substring(len);
    }
}
