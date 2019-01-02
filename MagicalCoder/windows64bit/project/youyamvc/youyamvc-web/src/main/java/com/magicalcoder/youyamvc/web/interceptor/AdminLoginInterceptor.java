package com.magicalcoder.youyamvc.web.interceptor;

import com.magicalcoder.youyamvc.app.adminuser.dto.AdminUserDto;
import com.magicalcoder.youyamvc.app.adminuser.dto.ModuleAndCategoryDto;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminRoleModulePriorityService;
import com.magicalcoder.youyamvc.app.adminuser.util.AdminUserContextUtil;
import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.spring.SpringContainer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginPagingController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class AdminLoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod)handler;
        Object hmBean = hm.getBean();
        if(hmBean instanceof AdminLoginController
                || hmBean instanceof AdminLoginPagingController){//验证后台用户登录情况
            String sessionId = AdminUserContextUtil.getSessionKey(req);
            if(StringUtils.isBlank(sessionId)){
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp");
                dispatcher .forward(req, res);
                return false;
            }
            AdminUserDto adminUserDto = CacheUtil.get(sessionId);
            if(adminUserDto==null){//未登录
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp");
                dispatcher .forward(req, res);
                return false;
            }
            req.setAttribute("userLeftModules",adminUserDto.getUserLeftModules());
        }
        return true;//无需验证登录

    }

}
