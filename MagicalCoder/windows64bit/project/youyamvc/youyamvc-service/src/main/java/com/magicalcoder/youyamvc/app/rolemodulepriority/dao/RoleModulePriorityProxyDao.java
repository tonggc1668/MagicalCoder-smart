package com.magicalcoder.youyamvc.app.rolemodulepriority.dao;

import com.magicalcoder.youyamvc.app.model.Module;
import com.magicalcoder.youyamvc.app.model.Priority;

import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
public interface RoleModulePriorityProxyDao{

    List<Module> roleHasModuleList(Map<String,Object> query);
    Priority roleModuleHasPriority(Map<String,Object> query);

}
