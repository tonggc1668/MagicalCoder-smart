package com.magicalcoder.youyamvc.core.common.utils;


public class HsCardsUtils {
	
	/**
	 * 格式字符串 按照format方式，高位不足补全foramt多出的部分
	 * @param str
	 * @param format
	 * @return ("AA","00000") = "000AA"
	 */
	 public static String formatStr(String str,String format){
		 int strLen = str.length();
	     int formatLen = format.length();
	     if (strLen < formatLen) {
	    	return  format.substring(0, formatLen-strLen)+str;
	     }
	     return str;
	 }
}
