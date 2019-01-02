package com.magicalcoder.youyamvc.core.tag.paging.style.impl;

import com.magicalcoder.youyamvc.core.tag.paging.dto.PagingDto;
import com.magicalcoder.youyamvc.core.tag.paging.style.IStyleExcuter;
import com.magicalcoder.youyamvc.core.tag.paging.utils.PagingConstant;
import com.magicalcoder.youyamvc.core.tag.paging.utils.PagingInfoUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 当没有countMethod配置时使用这种方式
 * @author hdy
 *
 */
@Component("styleExcuterWithoutCnt")
public class StyleExcuterWithoutCnt implements IStyleExcuter{

	@Override
	public String buildPageBar(Map queryMap,Map<String,String> barConfig) {
		PagingDto pd = (PagingDto) queryMap.get(PagingConstant.MAP_KEY_PAGING_PARAMS);
		//初始值
		int pIdx=pd.getPgIdx();//当前页
		//本次分页返回个数
//		int returnCount=pd.getPgCnt();
		//当前这一页返回的结果记录条数
		int pgCurrentCnt=pd.getPgCurrentCnt(); 
		//偏移量
		int excusion=PagingConstant.PAGING_EXCUTION;
		int pSize=pd.getPgSize();//每页数量
		
		int begin=1;
		int min=pIdx-excusion;
		int max=pIdx;
		if(min<begin+1){//最小值小于2
			min=begin+1;
		}
		String show = barConfig.get("show");
        if(show==null) show="";
		//是否隐藏首页
		boolean isChiefPageHide=pIdx==begin;
		isChiefPageHide = !show.contains("f");
		queryMap.put("pgIdx", 1);
		String chiefPage=isChiefPageHide?"":"<a class='c_page-first' href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>首页</a>";
		
		//是否隐藏上一页
		boolean isUpPageHide = pIdx==begin;
		queryMap.put("pgIdx",pd.getPgIdx()-1);
		String upPage=isUpPageHide?"":"<a class='c_page-prev' href='"+pd.getAction()+buildPagingInfo(queryMap)+"'><上一页</a>";
		//是否隐藏下一页
		queryMap.put("pgIdx", pd.getPgIdx()+1);
		String downPage="";
		if(pgCurrentCnt==pSize){
			downPage="<a class='c_page-next' href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>下一页></a>";
		}
		
		//是否加左省略号
		String leftShenglue="";
		if(min>2){//超过2就可以显示省略号了
			leftShenglue="<span>...</span>";
		}
		
		//右省略号
		String rigthShenglue="";
		if(pgCurrentCnt==pSize){
			rigthShenglue="<span>...</span>";
		}
		String mmStr="";
		for(int i=min;i<=max;i++){
			if(i!=pIdx){
				queryMap.put("pgIdx", i);
				mmStr+="<a href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>"+i+"</a>";
			}else{
				mmStr+="<a href='javascript:{}' class='cur' >"+i+"</a>";
			}
		}
		queryMap.put("pgIdx", 1);
		String pIdx1Str =  pIdx==1? "<a href='javascript:{}' class='cur'>1</a>":"<a href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>1</a>";
		return chiefPage+upPage+pIdx1Str+leftShenglue+mmStr+rigthShenglue+downPage;
	}
	private String buildPagingInfo(Map queryMap){
		return "?pagingInfo="+PagingInfoUtils.get().buildPagingInfo(queryMap);
	}
	
}
