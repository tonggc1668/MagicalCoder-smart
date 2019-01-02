package com.magicalcoder.youyamvc.core.tag.paging.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
public class PagingInfoUtils {

	private static PagingInfoUtils instance;

	public static PagingInfoUtils get() {
		if (instance == null) {
			synchronized (PagingInfoUtils.class) {
				if (instance == null) {
					instance = new PagingInfoUtils();
				}
			}
		}
		return instance;
	}
	public  String buildPagingInfo(Map queryMap) {
		StringBuffer pInfo = new StringBuffer();
		if (queryMap != null) {
			Set keySet = queryMap.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = queryMap.get(key);
				if(value==null){
					continue;
				}
				//过滤掉pd对象 pagingDto pd放在StyleExcuterWithCnt处理
				if(!PagingConstant.MAP_KEY_PAGING_PARAMS.equals(key)){
					pInfo.append(key).append("|");
					if(value instanceof Date){//如果是日期格式就保存long数值
						Date v = (Date)value;
						pInfo.append(v.getTime());
					}else{
						pInfo.append(value);
					}
					pInfo.append("||");
				}
			}
		}
		if(StringUtils.isBlank(pInfo)){
			return "";
		}
		return pInfo.substring(0, pInfo.length() - 2);
	}

	private String getValueFromPageInfo(String key, String pagingInfo) {
		if(StringUtils.isBlank(pagingInfo)){
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		String[] pairs = StringUtils.splitByWholeSeparator(pagingInfo, "||");
		for (int i = 0; i < pairs.length; i++) {
			String[] pair = StringUtils.split(pairs[i], '|');
			if (pair.length == 2) {
				params.put(pair[0], pair[1]);
			} else if (pair.length == 1) {// 对于空字符串处理
				params.put(pair[0], "");
			}
		}
		return (String) params.get(key);
	}

	public Integer getInt(String key, String pagingInfo) {
		String value = getValueFromPageInfo(key, pagingInfo);
		if (value != null && !value.equals("null"))
			return new Integer(value);
		else
			return null;
	}

	public String getString(String key, String pagingInfo) {
		return getValueFromPageInfo(key, pagingInfo);
	}

	public Long getLong(String key, String pagingInfo) {
		String value = getValueFromPageInfo(key, pagingInfo);
		if (value != null && !value.equals("null"))
			return new Long(value);
		else
			return null;
	}

	public Date getDate(String key, String pagingInfo) {
		String value = getValueFromPageInfo(key, pagingInfo);
		if (value != null) {
			long time;
			try {
				time = Long.valueOf(value);
				return new Date(time);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	public BigDecimal getBigDecimal(String key, String pagingInfo) {
		String value = getValueFromPageInfo(key, pagingInfo);
		if (value != null) {
			try {
				return new BigDecimal(value);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Map m = new HashMap();
		m.put("a", "");
		m.put("b", 1);
		m.put("c", new Date().getTime());
		String pi = get().buildPagingInfo(m);
		System.out.println(pi);
		System.out.println(get().getString("a", pi));
		System.out.println(get().getString("b", pi));
		System.out.println(get().getDate("c", pi));

	}

}
