package com.magicalcoder.youyamvc.core.tag.paging.tags;
/**
 * 处理<p:paging></p:paging>标签的
 */

import com.magicalcoder.youyamvc.core.spring.ControllerContext;
import com.magicalcoder.youyamvc.core.spring.SpringContainer;
import com.magicalcoder.youyamvc.core.tag.paging.IPaging;
import com.magicalcoder.youyamvc.core.tag.paging.dto.PagingDto;
import com.magicalcoder.youyamvc.core.tag.paging.utils.PagingConstant;
import com.magicalcoder.youyamvc.core.tag.paging.utils.PagingInfoUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PagingTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	//查询当前页列表的方法
	private String listMethod;
	//查询总记录数的方法
	private String countMethod;
	private String bean ;
	//每页显示多少条纪录
	private int pSize;
	@Override
	public int doStartTag() throws JspException {
		
		searchData();
		return super.doStartTag();
	}
	
	@Override
	public void doInitBody() throws JspException {
		
		super.doInitBody();
	}
	@Override
	public int doAfterBody() throws JspException {
		
		return (SKIP_BODY);
	}
	@Override
	public int doEndTag() throws JspException {
		
		String content = this.bodyContent.getString();
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(content);

        } catch (Exception e) {
        	e.printStackTrace();
        }
        return (EVAL_PAGE);
	}
	@Override
	public void release() {
		
		super.release();
	}
	public void searchData(){
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		// 取得当前action
		Object controller = getController(req);
		if(controller instanceof IPaging){
//--------初始化参数
			IPaging paging = (IPaging) controller;
			String pagingInfo = paging.getPagingInfo();
			//上一次的分页参数:为点页码服务
			PagingDto pd = new PagingDto();
			HttpServletRequest contextRequest = ControllerContext.getContext().getHttpServletRequest();
			pd.setAction(contextRequest.getRequestURL().toString());
			if(StringUtils.isNotBlank(pagingInfo)){//点分页了
				pd.setPgCnt(PagingInfoUtils.get().getInt("pgCnt", pagingInfo));
				pd.setPgIdx(PagingInfoUtils.get().getInt("pgIdx", pagingInfo));
			}
			//分页的查询条件
			Map<String,Object> queryMap = paging.getQueryMap();
			//顺序不能乱 下面queryList要使用这个key
			queryMap.put(PagingConstant.MAP_KEY_PAGING_PARAMS, pd);
			queryMap.put("pgIdx", pd.getPgIdx());
//--------pSize :每页显示多少条纪录  countMethod：是否系统自动切换分页方式
			pd.setPgSize(pSize);
			queryMap.put("pgSize", pd.getPgSize());
			if(StringUtils.isNotBlank(countMethod)){
				pd.setUseCountMethod(true);
			}
//--------pList			
			//本次分页查询的结果list
			List<Object> queryls = new ArrayList<Object>();
			//创建limit查询key值
			Integer pgIdx = (Integer) queryMap.get("pgIdx");
			Integer pgSize = (Integer) queryMap.get("pgSize");
			int firstLimit = (pgIdx-1)*pgSize;
			queryMap.put("firstLimit",firstLimit>=0 ? firstLimit:0 );
			queryMap.put("maxLimit", pgSize);
			queryls = getQueryList(bean,listMethod,queryMap);
			paging.setPageList(queryls);
//--------pCnt
			//查询总数
			int queryCount=0;
			if(pd.getPgCnt()<=0 && pd.isUseCountMethod()){//第一次 未点分页
				queryCount=this.getQueryCount(bean,countMethod,queryMap);
				pd.setPgCnt(queryCount);
			}
			queryMap.put("pgCnt", pd.getPgCnt());
//----------pgCurrentCnt			
			if(queryls!=null){
				pd.setPgCurrentCnt(queryls.size());
			}
			queryMap.put("pgCurrentCnt", pd.getPgCurrentCnt());
//---------页码交互
			//PagingStyle会有getAttribute操作
			req.setAttribute(PagingConstant.PAGING_REQUEST_KEY, queryMap);
		}else{
			throw new RuntimeException("PagingTag 使用分页标签，controller必须继承AdminPagingController");
		}
	}
	//反射获取list
	private <T> List<T> getQueryList(String bean,String listMethod,Map queryMap){
		//类名获取类
		Object sqlBeanObj = SpringContainer.getBean(bean);
		//方法名获取方法 null代表参数
		Object result = null;
		try {
			Method methodObj = sqlBeanObj.getClass().getMethod(listMethod, Map.class);
			result = methodObj.invoke(sqlBeanObj, new Object[]{queryMap});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return (List) result;
	}
	//反射获取总记录数
	private int getQueryCount(String bean,String countMethod,Map queryMap){
		//类名获取类
		Object sqlBeanObj = SpringContainer.getBean(bean);
		//方法名获取方法 null代表参数
		Object result = null;
		try {
			Method methodObj = sqlBeanObj.getClass().getMethod(countMethod, Map.class);
			result = methodObj.invoke(sqlBeanObj, new Object[]{queryMap});
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return (Integer) result;
	}
	private Object getController(HttpServletRequest req) {
		return ControllerContext.getContext().getCurrentController();
	}

	public String getListMethod() {
		return listMethod;
	}

	public void setListMethod(String listMethod) {
		this.listMethod = listMethod;
	}

	public String getCountMethod() {
		return countMethod;
	}

	public void setCountMethod(String countMethod) {
		this.countMethod = countMethod;
	}

	public String getBean() {
		return bean;
	}
	public void setBean(String bean) {
		this.bean = bean;
	}

	public int getpSize() {
		return pSize;
	}

	public void setpSize(int pSize) {
		this.pSize = pSize;
	}
}
