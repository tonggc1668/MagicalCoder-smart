package com.magicalcoder.youyamvc.core.common.utils;

import java.util.List;

/**
 * qq:799374340
 * @author hdy
 * 2013-6-17上午9:48:59
 */
public class ListUtils {
	public static boolean isBlank(List ls){
		return ls==null || ls.isEmpty();
	}
	public static boolean isNotBlank(List ls){
		return !isBlank(ls);
	}
}
