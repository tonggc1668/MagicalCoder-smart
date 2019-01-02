
package com.magicalcoder.youyamvc.app.classes.service.impl;

import com.magicalcoder.youyamvc.app.classes.dao.ClassesDao;
import com.magicalcoder.youyamvc.app.classes.service.ClassesService;
import com.magicalcoder.youyamvc.app.model.Classes;
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
@Component("classesService")
public class ClassesServiceImpl implements ClassesService{
    @Resource(name="classesDao")
    private ClassesDao classesDao;


    @Override
    public Classes getClasses(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return classesDao.getClasses(query);
    }

    @Override
    public Classes selectOneClassesWillThrowException(Map<String, Object> query){
        return classesDao.getClasses(query);
    }

    @Override
    public List<Classes> getClassesList(Map<String, Object> query) {
        return classesDao.getClassesList(query);
    }

    @Override
    public Integer getClassesListCount(Map<String, Object> query) {
        return classesDao.getClassesListCount(query);
    }

    @Override
    public     Long  insertClasses(Classes entity) {
        return classesDao.insertClasses(entity);
    }

    @Override
    public void updateClasses(Classes entity) {
        //校验
        classesDao.updateClasses(entity);
    }

    @Override
    public void updateClassesWithoutNull(Classes entity) {
        //校验
        classesDao.updateClassesWithoutNull(entity);
    }

    @Override
    public void updateClassesByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        classesDao.updateClassesByWhereSql(entity);
    }

    @Override
    public void deleteClasses(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        classesDao.deleteClasses(query);
    }
    @Override
    public void deleteClassesList(Map<String,Object> entity){
        classesDao.deleteClassesList(entity);
    }

    @Override
    public void deleteClassesByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        classesDao.deleteClassesByWhereSql(query);
    }

    @Override
    public List<Classes> getClassesOneToOneRelateList(Map<String, Object> query){
        return classesDao.getClassesOneToOneRelateList(query);
    }
    @Override
    public Integer getClassesOneToOneRelateListCount(Map<String, Object> query){
        return classesDao.getClassesOneToOneRelateListCount(query);
    }

    @Override
    public void truncateClasses() {
        classesDao.truncateClasses();
    }

    @Override
    public void batchInsertClasses(List<Classes> list) {
        //校验
        classesDao.batchInsertClasses(list);
    }

    @Override
    public void batchUpdateClasses(List<Classes> list) {
        //校验
        classesDao.batchUpdateClasses(list);
    }
    @Override
    public void batchDeleteClasses(List<Long> idList) {
        classesDao.batchDeleteClasses(idList);
    }

    @Override
    public void batchDeleteClassesList(List<Classes> entityList){
        classesDao.batchDeleteClassesList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<Classes> list) {
        if(list!=null && list.size()>0){
            for(Classes classes : list){
                if (classes.getId() == null) {
                    insertClasses(classes);
                } else {
                    Classes entity = getClasses(classes.getId());
                    if(entity==null){
                        insertClasses(classes);
                    }else {
                        CopyerSpringUtil.copyProperties(classes, entity);
                        updateClasses(entity);
                    }
                }
            }
        }
    }


    public List<Classes> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<Classes> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getClassesList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("classNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("studentCountFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("schoolIdFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
        }
        return new ArrayList<Classes>();
    }

    private List<Classes> searchList(String field,String keyword){
        List<Classes> list = getClassesList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getClassesList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
