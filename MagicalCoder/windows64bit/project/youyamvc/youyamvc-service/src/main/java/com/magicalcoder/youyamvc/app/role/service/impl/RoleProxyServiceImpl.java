package com.magicalcoder.youyamvc.app.role.service.impl;

import com.magicalcoder.youyamvc.app.role.dao.RoleProxyDao;
import com.magicalcoder.youyamvc.app.role.service.RoleService;
import com.magicalcoder.youyamvc.app.role.service.RoleProxyService;
import com.magicalcoder.youyamvc.app.model.Role;

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
@Component("roleProxyService")
public class RoleProxyServiceImpl implements RoleProxyService{

    @Resource(name="roleProxyDao")
    private RoleProxyDao roleProxyDao;

    @Resource(name="roleService")
    private RoleService roleService;
 

}
