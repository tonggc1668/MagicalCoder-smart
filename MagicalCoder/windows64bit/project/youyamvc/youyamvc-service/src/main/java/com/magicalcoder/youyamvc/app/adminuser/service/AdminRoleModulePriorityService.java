package com.magicalcoder.youyamvc.app.adminuser.service;

import com.magicalcoder.youyamvc.app.adminuser.dto.ModuleAndCategoryDto;
import com.magicalcoder.youyamvc.app.model.Module;
import com.magicalcoder.youyamvc.app.model.Priority;

import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public interface AdminRoleModulePriorityService {
    /**
     * 角色拥有哪些模块
     * @param roleId
     * @return
     */
    List<Module> roleHasModuleList(Long roleId);

    /**
     * 角色关联的模块名称拥有哪些权限
     * @param roleId
     * @param moduleName
     * @return
     */
    Priority roleModuleHasPriority(Long roleId,String moduleName);

    /**
     * 角色对应的左侧模块列表
     * @param roleId
     * @return
     */
    List<ModuleAndCategoryDto> userLeftModules(Long roleId);
}
