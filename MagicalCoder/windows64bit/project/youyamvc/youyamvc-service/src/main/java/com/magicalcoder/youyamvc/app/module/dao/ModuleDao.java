package com.magicalcoder.youyamvc.app.module.dao;

import com.magicalcoder.youyamvc.app.model.Module;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface ModuleDao{
    Module getModule(Map<String, Object> query);
    List<Module> getModuleList(Map<String, Object> query);
    Integer getModuleListCount(Map<String, Object> query);

    Long  insertModule(Module entity);    void batchInsertModule(List<Module> list);
    void batchUpdateModule(List<Module> list);
    void updateModule(Module entity);
    void updateModuleWithoutNull(Module entity);
    void updateModuleByWhereSql(Map<String,Object> entity);

    //oneToOne
    List<Module> getModuleOneToOneRelateList(Map<String, Object> query);
    Integer getModuleOneToOneRelateListCount(Map<String, Object> query);
    void truncateModule();
    void deleteModule(Map<String, Object> query);
    void deleteModuleList(Map<String, Object> query);
    void deleteModuleByWhereSql(Map<String, Object> query);
    void batchDeleteModule(List<Long> list);
    void batchDeleteModuleList(List<Module> entityList);
}
