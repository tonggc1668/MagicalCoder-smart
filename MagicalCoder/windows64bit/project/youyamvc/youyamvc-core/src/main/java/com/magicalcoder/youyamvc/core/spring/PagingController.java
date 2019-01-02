package com.magicalcoder.youyamvc.core.spring;

import com.magicalcoder.youyamvc.core.tag.paging.IPaging;

import java.util.List;
import java.util.Map;

/**
 * qq:799374340
 * @author hdy
 * 2013-7-25下午1:28:45
 */
public abstract class PagingController<T> extends IPaging<T>{

	@Override
	public abstract Map<String, Object> getQueryMap() ;

	@Override
	public abstract void setPageList(List<T> ls);

	public String getPagingInfo() {
		return ControllerContext.getContext().getHttpServletRequest().getParameter("pagingInfo");
	}

}
