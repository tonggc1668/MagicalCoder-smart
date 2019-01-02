package com.magicalcoder.youyamvc.web.controller.admin;

import com.magicalcoder.youyamvc.app.adminuser.AdminUserConstant;
import com.magicalcoder.youyamvc.app.adminuser.dto.AdminUserDto;
import com.magicalcoder.youyamvc.app.adminuser.dto.ModuleAndCategoryDto;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminRoleModulePriorityService;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
import com.magicalcoder.youyamvc.app.model.AdminUser;
import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;
import com.magicalcoder.youyamvc.core.common.safe.SqlInjectFilter;
import com.magicalcoder.youyamvc.core.common.safe.XssHtmlFilter;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;
import com.magicalcoder.youyamvc.core.identifyingcode.CreateIdentifyingCode;
import com.magicalcoder.youyamvc.core.identifyingcode.IdentifyingCodeConstant;
import com.magicalcoder.youyamvc.core.spring.SpringContainer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台人员登录
 *hdy qq:799374340
 *2013-6-8 下午5:05:27
 */
@Controller
public class AdminLoginAction {
	@Resource(name="adminUserService")
	private AdminUserService adminUserService;

    @RequestMapping(value="/admin/login")
    public String login(HttpServletRequest request,HttpServletResponse response,ModelMap model){
        setCheckCodeCookie(request, response);
        return "admin/login";
    }

    private void setCheckCodeCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie sessionCookie = new Cookie(IdentifyingCodeConstant.CHECK_CODE_SESSION_KEY,request.getSession().getId());
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
    }

    @RequestMapping(value="/admin")
	public String execute(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
        //验证登陆
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        //验证码
        String checkCode = request.getParameter("checkCode");
        String sessionId = request.getSession().getId();
        model.addAttribute("userName",userName);
        model.addAttribute("password",password);
        try {
            CreateIdentifyingCode code = new CreateIdentifyingCode();
            String cookieKey = code.getCookieKey(request);
            String sesionCheckCode = CacheUtil.get(IdentifyingCodeConstant.CHECK_CODE_SESSION_KEY+cookieKey);
            if(checkCode==null || sesionCheckCode==null || !checkCode.toLowerCase().equals(sesionCheckCode.toLowerCase())){
                setCheckCodeCookie(request, response);
                model.addAttribute("alertMsg","验证码错误");
                return "admin/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        XssHtmlFilter xss1 = new XssHtmlFilter();
        if(!xss1.isContainXss(userName) && !SqlInjectFilter.isContainSqlInject(userName) &&
                !xss1.isContainXss(password) && !SqlInjectFilter.isContainSqlInject(password)){
        }else{
            setCheckCodeCookie(request, response);
            model.addAttribute("alertMsg","非法参数");
            return "admin/login";
        }
		//查询库 验证此用户是否合法
        Map<String,Object> query = new HashMap<String, Object>();
        query.put("userName", userName);
        List<AdminUser> userList = adminUserService.getAdminUserList(query);
        if(ListUtils.isBlank(userList)){
            model.addAttribute("alertMsg","用户名不存在");
            setCheckCodeCookie(request, response);
            return "admin/login";
        }

        AdminUser adminUser = userList.get(0);
        String passwd = adminUser.getPassword();
        if(!passwd.equals(password)){
            model.addAttribute("alertMsg","密码不正确");
            setCheckCodeCookie(request, response);
            return "admin/login";
        }
        //保护密码
        adminUser.setPassword("***");
        //cookie

        Cookie sessionCookie = new Cookie(AdminUserConstant.ADMIN_LOGIN_COOKIE_KEY,sessionId);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
        AdminUserDto adminUserDto = new AdminUserDto();
        CopyerSpringUtil.copyProperties(adminUser, adminUserDto);
        //左侧模块
        Long roleId = adminUserDto.getRoleId();
        AdminRoleModulePriorityService adminRoleModulePriorityService = SpringContainer.getBean("adminRoleModulePriorityService", AdminRoleModulePriorityService.class);
        List<ModuleAndCategoryDto> userLeftModules = adminRoleModulePriorityService.userLeftModules(roleId);
        adminUserDto.setUserLeftModules(userLeftModules);
        //存储到缓存
        CacheUtil.resetCache(sessionId, AdminUserConstant.ADMIN_LOGIN_TIMEOUT, adminUserDto);
        model.remove("userName");
        model.remove("password");

        return "redirect:admin/index";//index.jsp 后台主页
	}

}
