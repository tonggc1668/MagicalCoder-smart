package com.magicalcoder.youyamvc.core.tag.paging.utils;

public interface PagingConstant {
	//本次request请求 key值
	String PAGING_REQUEST_KEY="_PAGING_REQUEST_KEY_123";
	//Map 上次分页参数Key
	String MAP_KEY_PAGING_PARAMS="_pParams_123";
	//记录总数
	String MAP_KEY_PAGING_COUNT="_pSumCount_123";
	//当前页码
	String MAP_KEY_PAGING_INDEX="_pIndex_123";
	//每页多少条
	String MAP_KEY_PAGING_PER_COUNT="_pPerCount_123";
	
	//paging-style 类型
	int PAGING_TYPE_WHIT_CNT=1;//有总数
	int PAGING_TYPE_WHITOUT_CNT=2;//无总数分页
	// paging excution 
	int PAGING_EXCUTION = 3;//页码前后偏移量
}
