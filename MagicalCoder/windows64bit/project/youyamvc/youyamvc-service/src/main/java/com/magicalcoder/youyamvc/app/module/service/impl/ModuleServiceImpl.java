
package com.magicalcoder.youyamvc.app.module.service.impl;

import com.magicalcoder.youyamvc.app.module.dao.ModuleDao;
import com.magicalcoder.youyamvc.app.module.service.ModuleService;
import com.magicalcoder.youyamvc.app.model.Module;
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
@Component("moduleService")
public class ModuleServiceImpl implements ModuleService{
    @Resource(name="moduleDao")
    private ModuleDao moduleDao;


    @Override
    public Module getModule(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return moduleDao.getModule(query);
    }

    @Override
    public Module selectOneModuleWillThrowException(Map<String, Object> query){
        return moduleDao.getModule(query);
    }

    @Override
    public List<Module> getModuleList(Map<String, Object> query) {
        return moduleDao.getModuleList(query);
    }

    @Override
    public Integer getModuleListCount(Map<String, Object> query) {
        return moduleDao.getModuleListCount(query);
    }

    @Override
    public     Long  insertModule(Module entity) {
        return moduleDao.insertModule(entity);
    }

    @Override
    public void updateModule(Module entity) {
        //校验
        moduleDao.updateModule(entity);
    }

    @Override
    public void updateModuleWithoutNull(Module entity) {
        //校验
        moduleDao.updateModuleWithoutNull(entity);
    }

    @Override
    public void updateModuleByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        moduleDao.updateModuleByWhereSql(entity);
    }

    @Override
    public void deleteModule(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        moduleDao.deleteModule(query);
    }
    @Override
    public void deleteModuleList(Map<String,Object> entity){
        moduleDao.deleteModuleList(entity);
    }

    @Override
    public void deleteModuleByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        moduleDao.deleteModuleByWhereSql(query);
    }

    @Override
    public List<Module> getModuleOneToOneRelateList(Map<String, Object> query){
        return moduleDao.getModuleOneToOneRelateList(query);
    }
    @Override
    public Integer getModuleOneToOneRelateListCount(Map<String, Object> query){
        return moduleDao.getModuleOneToOneRelateListCount(query);
    }

    @Override
    public void truncateModule() {
        moduleDao.truncateModule();
    }

    @Override
    public void batchInsertModule(List<Module> list) {
        //校验
        moduleDao.batchInsertModule(list);
    }

    @Override
    public void batchUpdateModule(List<Module> list) {
        //校验
        moduleDao.batchUpdateModule(list);
    }
    @Override
    public void batchDeleteModule(List<Long> idList) {
        moduleDao.batchDeleteModule(idList);
    }

    @Override
    public void batchDeleteModuleList(List<Module> entityList){
        moduleDao.batchDeleteModuleList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<Module> list) {
        if(list!=null && list.size()>0){
            for(Module module : list){
                if (module.getId() == null) {
                    insertModule(module);
                } else {
                    Module entity = getModule(module.getId());
                    if(entity==null){
                        insertModule(module);
                    }else {
                        CopyerSpringUtil.copyProperties(module, entity);
                        updateModule(entity);
                    }
                }
            }
        }
    }


    public List<Module> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<Module> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getModuleList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("moduleNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("moduleUrlFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("moduleCategoryIdFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            list = searchList("moduleTitleFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return new ArrayList<Module>();
    }

    private List<Module> searchList(String field,String keyword){
        List<Module> list = getModuleList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getModuleList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
