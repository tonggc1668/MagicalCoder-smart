package com.magicalcoder.youyamvc.app.classes.dao.impl;

import com.magicalcoder.youyamvc.app.classes.dao.ClassesDao;
import com.magicalcoder.youyamvc.app.model.Classes;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@Component("classesDao")
public class ClassesDaoImpl   implements ClassesDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public Classes getClasses(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ClassesMapper.getClasses",query);
    }
    @Override
    public List<Classes> getClassesList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("ClassesMapper.getClassesList", query);
    }
    @Override
    public Integer getClassesListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ClassesMapper.getClassesListCount", query);
    }

    @Override
    public void batchInsertClasses(List<Classes> list) {
        sqlSessionTemplate.insert("ClassesMapper.batchInsertClasses",list);
    }

    @Override
    public void batchUpdateClasses(List<Classes> list) {
        sqlSessionTemplate.update("ClassesMapper.batchUpdateClasses",list);
    }

    @Override
    public     Long  insertClasses(Classes entity) {
        sqlSessionTemplate.insert("ClassesMapper.insertClasses",entity);
  return entity.getId();    }
    @Override
    public void updateClasses(Classes entity) {
        sqlSessionTemplate.update("ClassesMapper.updateClasses", entity);
    }
    @Override
    public void updateClassesWithoutNull(Classes entity) {
        sqlSessionTemplate.update("ClassesMapper.updateClassesWithoutNull", entity);
    }
    @Override
    public void updateClassesByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("ClassesMapper.updateClassesByWhereSql", entity);
    }

    @Override
    public List<Classes> getClassesOneToOneRelateList(Map<String, Object> query){
        return sqlSessionTemplate.selectList("ClassesMapper.getClassesOneToOneRelateList", query);
    }
    @Override
    public Integer getClassesOneToOneRelateListCount(Map<String, Object> query){
        return sqlSessionTemplate.selectOne("ClassesMapper.getClassesOneToOneRelateListCount", query);
    }
    @Override
    public void truncateClasses() {
        sqlSessionTemplate.delete("ClassesMapper.truncateClasses");
    }
    @Override
    public void deleteClasses(Map<String, Object> query) {
        sqlSessionTemplate.delete("ClassesMapper.deleteClasses",query);
    }
    @Override
    public void deleteClassesList(Map<String, Object> query) {
        sqlSessionTemplate.delete("ClassesMapper.deleteClassesList",query);
    }
    @Override
    public void deleteClassesByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("ClassesMapper.deleteClassesByWhereSql",query);
    }
    @Override
    public void batchDeleteClasses(List<Long> list) {
        sqlSessionTemplate.delete("ClassesMapper.batchDeleteClasses",list);
    }
    @Override
    public void batchDeleteClassesList(List<Classes> list) {
        sqlSessionTemplate.delete("ClassesMapper.batchDeleteClassesList",list);
    }

}
