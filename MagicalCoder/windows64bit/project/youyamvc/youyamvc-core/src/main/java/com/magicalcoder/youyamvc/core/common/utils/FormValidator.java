package com.magicalcoder.youyamvc.core.common.utils;

import com.magicalcoder.youyamvc.core.common.exception.BusinessException;
import com.magicalcoder.youyamvc.core.common.utils.log.Log4jUtils;

import java.util.Map;

/**
 * qq:799374340
 * @author hdy
 * 2013-7-17下午3:17:34
 */
public class FormValidator {

	/** 验证参数不能为null 空字符串
	 * @param reqMap get("id")
	 * @param inputs id,name,fundCode
	 * @param logClazz 日志
	 */
	public static void StringNotBlank(Map<String, String> reqMap,String inputs,Class<?> logClazz){
		String[] strArray = inputs.split(",");
		if(strArray==null || strArray.length<=0){
			return;
		}
		StringBuilder sbnull = new StringBuilder();
		StringBuilder sbblank = new StringBuilder();
		for(int i=0;i<strArray.length;i++){
			String input = strArray[i];
			if(reqMap.get(input)==null){
				sbnull.append(input).append(",");
			}else if("".equals(reqMap.get(input).trim())){
				sbblank.append(input).append(",");
			}
		}
		if(StringUtils.isNotBlank(sbnull.toString())){
			String message = "入参"+sbnull.substring(0, sbnull.length()-1)+"为必传参数 结果却不存在";
			Log4jUtils.getLog(logClazz).error(message);
			throw new BusinessException("BUS-C00404", message);
		}
		if(( StringUtils.isNotBlank(sbblank.toString()) )){
			String message = "入参"+sbblank.substring(0, sbblank.length()-1)+"不能为空字符串";
			Log4jUtils.getLog(logClazz).error(message);
			throw new BusinessException("BUS-C00402", message);
		}
	}
	
}
