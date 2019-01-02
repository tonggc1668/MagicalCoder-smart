package com.magicalcoder.youyamvc.core.tag.paging.tags;
/**
 * 处理<h:paging-style type="1"/>
 */

import com.magicalcoder.youyamvc.core.spring.SpringContainer;
import com.magicalcoder.youyamvc.core.tag.paging.dto.PagingDto;
import com.magicalcoder.youyamvc.core.tag.paging.style.IStyleExcuter;
import com.magicalcoder.youyamvc.core.tag.paging.utils.PagingConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.HashMap;
import java.util.Map;

public class PagingStyle extends BodyTagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//选择那种方式
	private int type;
	//
	private String show;
	//考虑当前页 跟每页数量
	@Override
	public int doEndTag() throws JspException {
//		System.out.println("----------分页展示----------");
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		Map queryMap = (Map) req.getAttribute(PagingConstant.PAGING_REQUEST_KEY);//这里应该使用localThread暂时先这样
		if(queryMap==null){
			throw new RuntimeException("使用分页标签，controller必须继承AdminPagingController");
		}
		PagingDto pd = (PagingDto) queryMap.get(PagingConstant.MAP_KEY_PAGING_PARAMS);
		IStyleExcuter excuter = null;
		if(!pd.isUseCountMethod()){
			excuter = SpringContainer.getBean("styleExcuterWithoutCnt", IStyleExcuter.class);
		}else{
			if(type == 0){//默认使用简单方式 无pCnt
				excuter = SpringContainer.getBean("styleExcuterWithoutCnt", IStyleExcuter.class);
			}else if(type==PagingConstant.PAGING_TYPE_WHIT_CNT){
				excuter = SpringContainer.getBean("styleExcuterWithCnt", IStyleExcuter.class);
			}else if(type==PagingConstant.PAGING_TYPE_WHITOUT_CNT){
				excuter = SpringContainer.getBean("styleExcuterWithoutCnt", IStyleExcuter.class);
			}
		}
		if(excuter==null){
			throw new RuntimeException("标签<*:paging-style type='*'/>未设置存在的type值 类型");
		}
		Map<String,String> barConfig = new HashMap<String,String>();
		barConfig.put("show", show);
		String outPageBar = excuter.buildPageBar(queryMap,barConfig);
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(outPageBar);

        } catch (Exception e) {
        	e.printStackTrace();
        }
        return (EVAL_PAGE);
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	
}
