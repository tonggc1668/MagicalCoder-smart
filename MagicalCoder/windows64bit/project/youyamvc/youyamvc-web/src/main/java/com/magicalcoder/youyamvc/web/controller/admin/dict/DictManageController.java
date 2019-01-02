package com.magicalcoder.youyamvc.web.controller.admin.dict;

import com.magicalcoder.youyamvc.app.dict.service.DictProxyService;
import com.magicalcoder.youyamvc.app.dict.service.DictService;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class DictManageController extends AdminLoginController{
	private DictProxyService dictProxyService;
  	//刷新数据字典
	@RequestMapping(value = "/admin/dict/refresh", method = RequestMethod.GET)  
    public String cardTool() {
		dictProxyService.refresh();
        return "success";
    }
  	
}
