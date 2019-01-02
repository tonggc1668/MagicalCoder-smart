package com.magicalcoder.youyamvc.web.controller.admin;

import com.magicalcoder.youyamvc.app.adminuser.dto.AdminUserDto;
import com.magicalcoder.youyamvc.app.adminuser.util.AdminUserContextUtil;
import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by www.magicalcoder.com on 14-8-20.
 * 799374340@qq.com
 */
@Controller
public class AdminIndexController extends AdminLoginController{

    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request,ModelMap model) {
        return "admin/index";
    }

    @RequestMapping(value = "/admin/loginMessage", method = RequestMethod.GET)
    public void loginMessage(HttpServletRequest request,HttpServletResponse response,ModelMap model){
        String sessionId = AdminUserContextUtil.getSessionKey(request);
        AdminUserDto adminUser = CacheUtil.get(sessionId);
        Map<String,String> map = new HashMap<String,String>();
        map.put("userName",adminUser.getUserName());
        toJson(response, new AjaxData("",map));
    }

    @RequestMapping(value="/admin/loginOut")
    public String loginOut(HttpServletRequest request,HttpServletResponse response,ModelMap model){
        String sessionId = AdminUserContextUtil.getSessionKey(request);
        CacheUtil.delete(sessionId);
        return "admin/login";

    }
}
