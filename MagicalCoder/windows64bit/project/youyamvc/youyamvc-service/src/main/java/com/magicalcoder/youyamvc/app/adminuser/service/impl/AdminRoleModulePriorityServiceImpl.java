package com.magicalcoder.youyamvc.app.adminuser.service.impl;

import com.magicalcoder.youyamvc.app.adminuser.dto.ModuleAndCategoryDto;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminRoleModulePriorityService;
import com.magicalcoder.youyamvc.app.model.Module;
import com.magicalcoder.youyamvc.app.model.ModuleCategory;
import com.magicalcoder.youyamvc.app.model.Priority;
import com.magicalcoder.youyamvc.app.modulecategory.service.ModuleCategoryService;
import com.magicalcoder.youyamvc.app.rolemodulepriority.dao.RoleModulePriorityProxyDao;
import com.magicalcoder.youyamvc.core.cache.xmemcached.anotation.XMemcachedParam;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.MapUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
@Component("adminRoleModulePriorityService")
public class AdminRoleModulePriorityServiceImpl implements AdminRoleModulePriorityService {
    @Resource(name="roleModulePriorityProxyDao")
    private RoleModulePriorityProxyDao roleModulePriorityProxyDao;

    @Resource
    private ModuleCategoryService moduleCategoryService;
    @Override
    public List<Module> roleHasModuleList(Long roleId) {
        return roleModulePriorityProxyDao.roleHasModuleList(MapUtil.buildMap("roleId",roleId));
    }

    @Override
    public Priority roleModuleHasPriority(Long roleId, String moduleName) {
        return roleModulePriorityProxyDao.roleModuleHasPriority(MapUtil.buildMap("roleId", roleId, "moduleName", moduleName));
    }

    @Override
    public List<ModuleAndCategoryDto> userLeftModules(Long roleId) {
        if(roleId==null){
            return null;
        }
        List<Module> moduleList = roleHasModuleList(roleId);
        List<ModuleCategory> categoryList = moduleCategoryService.getModuleCategoryList(MapUtil.buildMap("orderBy", "sort_num asc"));
        List<ModuleAndCategoryDto> categoryDtoList = new ArrayList<ModuleAndCategoryDto>();
        if(ListUtils.isNotBlank(moduleList)&&ListUtils.isNotBlank(categoryList)){
            for(ModuleCategory category:categoryList){
                List<Module> findModules = findModules(category.getId(),moduleList);
                if(ListUtils.isNotBlank(findModules)){
                    ModuleAndCategoryDto dto = new ModuleAndCategoryDto();
                    dto.setCategory(category);
                    dto.setModuleList(findModules);
                    categoryDtoList.add(dto);
                }
            }
        }
        return categoryDtoList;
    }
    private List<Module> findModules(Long categoryId,List<Module> modules){
        List<Module> list = new ArrayList<Module>();
        for(int i=0;i<modules.size();i++){
            Module module = modules.get(i);
            if(module.getModuleCategoryId()!=null)
                if(module.getModuleCategoryId().compareTo(categoryId)==0){
                    list.add(module);
                    modules.remove(i);
                    i--;
                }
        }
        return list;
    }
}
