package com.magicalcoder.youyamvc.core.tag.paging.style;

import java.util.Map;

/**
 * 处理分页 页码的接口
 * @author Administrator
 *
 */
public interface IStyleExcuter {

	String buildPageBar(Map queryMap,Map<String,String> barConfig);
}
