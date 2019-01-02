
package com.magicalcoder.youyamvc.app.modulecategory.service.impl;

import com.magicalcoder.youyamvc.app.modulecategory.dao.ModuleCategoryDao;
import com.magicalcoder.youyamvc.app.modulecategory.service.ModuleCategoryService;
import com.magicalcoder.youyamvc.app.model.ModuleCategory;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@Component("moduleCategoryService")
public class ModuleCategoryServiceImpl implements ModuleCategoryService{
    @Resource(name="moduleCategoryDao")
    private ModuleCategoryDao moduleCategoryDao;


    @Override
    public ModuleCategory getModuleCategory(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return moduleCategoryDao.getModuleCategory(query);
    }

    @Override
    public ModuleCategory selectOneModuleCategoryWillThrowException(Map<String, Object> query){
        return moduleCategoryDao.getModuleCategory(query);
    }

    @Override
    public List<ModuleCategory> getModuleCategoryList(Map<String, Object> query) {
        return moduleCategoryDao.getModuleCategoryList(query);
    }

    @Override
    public Integer getModuleCategoryListCount(Map<String, Object> query) {
        return moduleCategoryDao.getModuleCategoryListCount(query);
    }

    @Override
    public     Long  insertModuleCategory(ModuleCategory entity) {
        return moduleCategoryDao.insertModuleCategory(entity);
    }

    @Override
    public void updateModuleCategory(ModuleCategory entity) {
        //校验
        moduleCategoryDao.updateModuleCategory(entity);
    }

    @Override
    public void updateModuleCategoryWithoutNull(ModuleCategory entity) {
        //校验
        moduleCategoryDao.updateModuleCategoryWithoutNull(entity);
    }

    @Override
    public void updateModuleCategoryByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        moduleCategoryDao.updateModuleCategoryByWhereSql(entity);
    }

    @Override
    public void deleteModuleCategory(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        moduleCategoryDao.deleteModuleCategory(query);
    }
    @Override
    public void deleteModuleCategoryList(Map<String,Object> entity){
        moduleCategoryDao.deleteModuleCategoryList(entity);
    }

    @Override
    public void deleteModuleCategoryByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        moduleCategoryDao.deleteModuleCategoryByWhereSql(query);
    }


    @Override
    public void truncateModuleCategory() {
        moduleCategoryDao.truncateModuleCategory();
    }

    @Override
    public void batchInsertModuleCategory(List<ModuleCategory> list) {
        //校验
        moduleCategoryDao.batchInsertModuleCategory(list);
    }

    @Override
    public void batchUpdateModuleCategory(List<ModuleCategory> list) {
        //校验
        moduleCategoryDao.batchUpdateModuleCategory(list);
    }
    @Override
    public void batchDeleteModuleCategory(List<Long> idList) {
        moduleCategoryDao.batchDeleteModuleCategory(idList);
    }

    @Override
    public void batchDeleteModuleCategoryList(List<ModuleCategory> entityList){
        moduleCategoryDao.batchDeleteModuleCategoryList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<ModuleCategory> list) {
        if(list!=null && list.size()>0){
            for(ModuleCategory moduleCategory : list){
                if (moduleCategory.getId() == null) {
                    insertModuleCategory(moduleCategory);
                } else {
                    ModuleCategory entity = getModuleCategory(moduleCategory.getId());
                    if(entity==null){
                        insertModuleCategory(moduleCategory);
                    }else {
                        CopyerSpringUtil.copyProperties(moduleCategory, entity);
                        updateModuleCategory(entity);
                    }
                }
            }
        }
    }


    public List<ModuleCategory> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<ModuleCategory> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getModuleCategoryList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("moduleCategoryNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return new ArrayList<ModuleCategory>();
    }

    private List<ModuleCategory> searchList(String field,String keyword){
        List<ModuleCategory> list = getModuleCategoryList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getModuleCategoryList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
