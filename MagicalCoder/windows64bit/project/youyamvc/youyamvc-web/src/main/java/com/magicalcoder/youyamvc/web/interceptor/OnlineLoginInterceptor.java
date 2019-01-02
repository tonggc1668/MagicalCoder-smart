package com.magicalcoder.youyamvc.web.interceptor;

import com.magicalcoder.youyamvc.app.userweb.dto.UserWebDto;
import com.magicalcoder.youyamvc.app.userweb.util.UserWebUtil;
import com.magicalcoder.youyamvc.core.common.dto.JsonData;
import com.magicalcoder.youyamvc.core.spring.JsonWrite;
import com.magicalcoder.youyamvc.core.spring.web.WebLoginController;
import com.magicalcoder.youyamvc.web.common.StatusConstant;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OnlineLoginInterceptor extends JsonWrite implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//System.out.println("loginInterceptor-afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		//System.out.println("loginInterceptor-postHandle");
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
							 Object handler) throws Exception {

		HandlerMethod hm = (HandlerMethod)handler;
		Object hmBean = hm.getBean();
		if(hmBean instanceof WebLoginController){//验证前台用户登录情况
			UserWebDto userDto = UserWebUtil.getUserDtoFromCache(req);
			if(userDto==null){//未登录
				toJsonData(res, new JsonData.Builder(null).code(StatusConstant.NEED_LOGIN).message("未登录").build());
				return false;
			}else {
				UserWebUtil.setUserDtoToRequest(req,userDto);//设置用户信息到request中
			}
		}
		return true;//已登录
	}

}
