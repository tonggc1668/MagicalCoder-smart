package com.magicalcoder.youyamvc.app.rolemodulepriority.service.impl;

import com.magicalcoder.youyamvc.app.rolemodulepriority.dao.RoleModulePriorityProxyDao;
import com.magicalcoder.youyamvc.app.rolemodulepriority.service.RoleModulePriorityService;
import com.magicalcoder.youyamvc.app.rolemodulepriority.service.RoleModulePriorityProxyService;
import com.magicalcoder.youyamvc.app.model.RoleModulePriority;

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
@Component("roleModulePriorityProxyService")
public class RoleModulePriorityProxyServiceImpl implements RoleModulePriorityProxyService{

    @Resource(name="roleModulePriorityProxyDao")
    private RoleModulePriorityProxyDao roleModulePriorityProxyDao;

    @Resource(name="roleModulePriorityService")
    private RoleModulePriorityService roleModulePriorityService;
 

}
