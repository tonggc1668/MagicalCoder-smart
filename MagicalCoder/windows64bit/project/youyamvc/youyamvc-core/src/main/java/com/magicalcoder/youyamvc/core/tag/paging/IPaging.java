package com.magicalcoder.youyamvc.core.tag.paging;

import java.util.List;
import java.util.Map;

public abstract class IPaging<T> {
	
	public abstract Map<String,Object> getQueryMap();
	public abstract void setPageList(List<T> ls);

	//分页记住上次的参数 其实是BaseAction中的
	public abstract String getPagingInfo();
}
