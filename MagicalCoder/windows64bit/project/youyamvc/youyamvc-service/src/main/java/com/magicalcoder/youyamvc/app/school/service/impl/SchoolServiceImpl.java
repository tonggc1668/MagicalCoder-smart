
package com.magicalcoder.youyamvc.app.school.service.impl;

import com.magicalcoder.youyamvc.app.school.dao.SchoolDao;
import com.magicalcoder.youyamvc.app.school.service.SchoolService;
import com.magicalcoder.youyamvc.app.model.School;
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
@Component("schoolService")
public class SchoolServiceImpl implements SchoolService{
    @Resource(name="schoolDao")
    private SchoolDao schoolDao;


    @Override
    public School getSchool(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return schoolDao.getSchool(query);
    }

    @Override
    public School selectOneSchoolWillThrowException(Map<String, Object> query){
        return schoolDao.getSchool(query);
    }

    @Override
    public List<School> getSchoolList(Map<String, Object> query) {
        return schoolDao.getSchoolList(query);
    }

    @Override
    public Integer getSchoolListCount(Map<String, Object> query) {
        return schoolDao.getSchoolListCount(query);
    }

    @Override
    public     Long  insertSchool(School entity) {
        return schoolDao.insertSchool(entity);
    }

    @Override
    public void updateSchool(School entity) {
        //校验
        schoolDao.updateSchool(entity);
    }

    @Override
    public void updateSchoolWithoutNull(School entity) {
        //校验
        schoolDao.updateSchoolWithoutNull(entity);
    }

    @Override
    public void updateSchoolByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        schoolDao.updateSchoolByWhereSql(entity);
    }

    @Override
    public void deleteSchool(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        schoolDao.deleteSchool(query);
    }
    @Override
    public void deleteSchoolList(Map<String,Object> entity){
        schoolDao.deleteSchoolList(entity);
    }

    @Override
    public void deleteSchoolByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        schoolDao.deleteSchoolByWhereSql(query);
    }


    @Override
    public void truncateSchool() {
        schoolDao.truncateSchool();
    }

    @Override
    public void batchInsertSchool(List<School> list) {
        //校验
        schoolDao.batchInsertSchool(list);
    }

    @Override
    public void batchUpdateSchool(List<School> list) {
        //校验
        schoolDao.batchUpdateSchool(list);
    }
    @Override
    public void batchDeleteSchool(List<Long> idList) {
        schoolDao.batchDeleteSchool(idList);
    }

    @Override
    public void batchDeleteSchoolList(List<School> entityList){
        schoolDao.batchDeleteSchoolList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<School> list) {
        if(list!=null && list.size()>0){
            for(School school : list){
                if (school.getId() == null) {
                    insertSchool(school);
                } else {
                    School entity = getSchool(school.getId());
                    if(entity==null){
                        insertSchool(school);
                    }else {
                        CopyerSpringUtil.copyProperties(school, entity);
                        updateSchool(entity);
                    }
                }
            }
        }
    }


    public List<School> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<School> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getSchoolList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("schoolNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("openFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("createTimeFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return new ArrayList<School>();
    }

    private List<School> searchList(String field,String keyword){
        List<School> list = getSchoolList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getSchoolList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
