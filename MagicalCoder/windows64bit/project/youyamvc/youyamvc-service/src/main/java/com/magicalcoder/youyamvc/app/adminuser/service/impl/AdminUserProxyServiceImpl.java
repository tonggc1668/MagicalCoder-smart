package com.magicalcoder.youyamvc.app.adminuser.service.impl;

import com.magicalcoder.youyamvc.app.adminuser.dao.AdminUserProxyDao;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserProxyService;
import com.magicalcoder.youyamvc.app.model.AdminUser;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.ArrayList;

/**
    代理类 保证了service dao的自动生成 有改动业务 直接写在此类处
*/
/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("adminUserProxyService")
public class AdminUserProxyServiceImpl implements AdminUserProxyService{

    @Resource(name="adminUserProxyDao")
    private AdminUserProxyDao adminUserProxyDao;

    @Resource(name="adminUserService")
    private AdminUserService adminUserService;
 

}
