package com.magicalcoder.youyamvc.app.modulecategory.dao;

import com.magicalcoder.youyamvc.app.model.ModuleCategory;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface ModuleCategoryDao{
    ModuleCategory getModuleCategory(Map<String, Object> query);
    List<ModuleCategory> getModuleCategoryList(Map<String, Object> query);
    Integer getModuleCategoryListCount(Map<String, Object> query);

    Long  insertModuleCategory(ModuleCategory entity);    void batchInsertModuleCategory(List<ModuleCategory> list);
    void batchUpdateModuleCategory(List<ModuleCategory> list);
    void updateModuleCategory(ModuleCategory entity);
    void updateModuleCategoryWithoutNull(ModuleCategory entity);
    void updateModuleCategoryByWhereSql(Map<String,Object> entity);

    void truncateModuleCategory();
    void deleteModuleCategory(Map<String, Object> query);
    void deleteModuleCategoryList(Map<String, Object> query);
    void deleteModuleCategoryByWhereSql(Map<String, Object> query);
    void batchDeleteModuleCategory(List<Long> list);
    void batchDeleteModuleCategoryList(List<ModuleCategory> entityList);
}
