package com.magicalcoder.youyamvc.core.tag.paging.style.impl;

import com.magicalcoder.youyamvc.core.tag.paging.dto.PagingDto;
import com.magicalcoder.youyamvc.core.tag.paging.style.IStyleExcuter;
import com.magicalcoder.youyamvc.core.tag.paging.utils.PagingConstant;
import com.magicalcoder.youyamvc.core.tag.paging.utils.PagingInfoUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 当有countMethod总数时使用这种方式
 * @author hdy
 *
 */
@Component("styleExcuterWithCnt")
public class StyleExcuterWithCnt  implements IStyleExcuter{

	@Override
	public String buildPageBar(Map queryMap,Map<String,String> barConfig) {
		PagingDto pd = (PagingDto) queryMap.get(PagingConstant.MAP_KEY_PAGING_PARAMS);
		//初始值
		int pIdx=pd.getPgIdx();//当前页
		int pSumCnt=pd.getPgCnt();//总记录数
		int pSize=pd.getPgSize();//每页数量
		//偏移量
		int excusion=PagingConstant.PAGING_EXCUTION;;
		
		//
		int begin=1;
		int mod=pSumCnt%pSize;
		int addOne=0;//是否除尽
		if(mod>0){
			addOne=1;
		}
		//分多少页
		int end = pSumCnt/pSize+addOne;
		//要显示的区域[min,max]
		int min=pIdx-excusion;
		int max=pIdx+excusion;

		if(min<begin+1){//最小值小于2
			min=begin+1;
		}
		if(max>end){
			max=end;
		}
		//构造pagingInfo参数
//		String params="";
		String show = barConfig.get("show");
		//是否隐藏首页
		boolean isChiefPageHide=pIdx<=begin;
		isChiefPageHide = !show.contains("f");
		queryMap.put("pgIdx", 1);
		String chiefPage=isChiefPageHide?"":"<a class='c_page-first' href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>首页</a>";
		//是否隐藏上一页
		boolean isUpPageHide = pIdx<=begin;
		queryMap.put("pgIdx", pd.getPgIdx()-1);
		String upPage=isUpPageHide?"":"<a class='c_page-prev' href='"+pd.getAction()+buildPagingInfo(queryMap)+"'><上一页</a>";
		//是否隐藏下一页
		boolean isDownPageHide = pIdx>=end;
		queryMap.put("pgIdx", pd.getPgIdx()+1);
		String downPage=isDownPageHide?"":"<a class='c_page-next' href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>下一页></a>";
		//是否隐藏末页
		boolean isEndPageHide = pIdx>=end;
		isEndPageHide=!show.contains("l");
		queryMap.put("pgIdx", end);
		String endPag=isEndPageHide?"":"<a class='c_page-last' href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>末页</a>";
		//
		String mmStr="";
		for(int i=min;i<=max;i++){
			if(i!=pIdx){
				queryMap.put("pgIdx", i);
				mmStr+="<a href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>"+i+"</a>";
			}else{
				mmStr+="<a href='javascript:{}' class='cur' >"+i+"</a>";
			}
		}
		//是否加左省略号
		String leftShenglue="";
		if(min>2){//超过2就可以显示省略号了
			leftShenglue="<span>...</span>";
		}
		//右省略号
		String rigthShenglue="";
		if(max<end){
			rigthShenglue="<span>...</span>";
		}
		queryMap.put("pgIdx", 1);
        if(pSumCnt<=0 || end<=1){
            return "";
        }
		//第一页
		String pIdx1Str =  pIdx==1? "<a href='javascript:{}' class='cur'>1</a>":"<a href='"+pd.getAction()+buildPagingInfo(queryMap)+"'>1</a>";
		String currPageStr =show.contains("p")?" <span>第"+pIdx+"页</span>":"";
		String pageStr = show.contains("s") ? " <span>共"+end+"页</span>":"";
		String sumPageStr = show.contains("a") ?( end>0 ?  pageStr+"<span>总记录"+pSumCnt+"条</span>": "<span>暂无数据</span>"):"";
		return chiefPage+upPage+pIdx1Str+leftShenglue+mmStr+rigthShenglue+downPage+endPag+currPageStr+sumPageStr;
	}
	private String buildPagingInfo(Map queryMap){
		return "?pagingInfo="+PagingInfoUtils.get().buildPagingInfo(queryMap);
	}
	
}
