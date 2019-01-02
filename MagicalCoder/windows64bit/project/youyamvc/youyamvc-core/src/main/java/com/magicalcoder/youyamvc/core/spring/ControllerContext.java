package com.magicalcoder.youyamvc.core.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * qq:799374340
 * @author hdy
 * 2013-7-25上午10:10:07 获取spring上下文
 * 废弃 严重: The web application [] created a ThreadLocal with key of type [java.lang.ThreadLocal]
 * (value [java.lang.ThreadLocal@3eda834c]) and a value of type [com.magicalcoder.youyamvc.core.spring.ControllerContext]
 * (value [com.magicalcoder.youyamvc.core.spring.ControllerContext@7be212d0]) but failed to remove it when the web application
 * was stopped. Threads are going to be renewed over time to try and avoid a probable memory leak.
 */
@Deprecated
public class ControllerContext {
	//ThreadLocal的key是当前线程对象 所以每次contextThread.get()都可能是空 当前线程对象已经改变 要重新赋值
	private static  ThreadLocal<Object> contextThread = new ThreadLocal<Object>();
	private Map<Object,Object> contextMap = new HashMap<Object,Object>();
	
	private String CONTROLLER="controller";
	private String REQUEST = "httpRequest";
	private String RESPONSE = "httpResponse";
	public ControllerContext(Map<Object,Object> contextMap){
		this.contextMap = contextMap;
	}
	//设置
	private static void setContext(ControllerContext controllerContext){
		contextThread.set(controllerContext);//放到当前线程对象中
	}
    //内存回收防止泄露
    public static void remove(){
        contextThread.remove();
    }
	//获取上下文对象
	public static ControllerContext getContext(){
		ControllerContext controllerContext = (ControllerContext)contextThread.get();//取当前线程对象
		if(null == controllerContext){//已经是下一次的请求或者非当前线程内操作了
			controllerContext = new ControllerContext(new HashMap<Object,Object>());
			setContext(controllerContext);
		}
		return controllerContext;
	}
	public void setController(Object controller){
		contextMap.put(CONTROLLER, controller);
	}
	public Object getCurrentController(){
		if(contextMap.get(CONTROLLER)!=null){
			return contextMap.get(CONTROLLER);
		}
		return null;
	}
	
	public void setHttpServletRequest(HttpServletRequest request){
		contextMap.put(REQUEST, request);
	}
	public HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest) contextMap.get(REQUEST);
	}
	public void setHttpServletResponse(HttpServletResponse response){
		contextMap.put(RESPONSE, response);
	}
	public HttpServletResponse getHttpServletResponse(){
		return (HttpServletResponse)contextMap.get(RESPONSE);
	}
	public ServletContext getServletContext(){
		return getHttpSession().getServletContext();
	}
	public HttpSession getHttpSession(){
		return getHttpServletRequest().getSession();
	}
}
