package com.magicalcoder.youyamvc.app.classteacher.dao.impl;

import com.magicalcoder.youyamvc.app.classteacher.dao.ClassTeacherDao;
import com.magicalcoder.youyamvc.app.model.ClassTeacher;
import com.magicalcoder.youyamvc.app.model.defined.ClassTeacherMany;
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
@Component("classTeacherDao")
public class ClassTeacherDaoImpl   implements ClassTeacherDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public ClassTeacher getClassTeacher(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ClassTeacherMapper.getClassTeacher",query);
    }
    @Override
    public List<ClassTeacher> getClassTeacherList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("ClassTeacherMapper.getClassTeacherList", query);
    }
    @Override
    public Integer getClassTeacherListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ClassTeacherMapper.getClassTeacherListCount", query);
    }

    @Override
    public void batchInsertClassTeacher(List<ClassTeacher> list) {
        sqlSessionTemplate.insert("ClassTeacherMapper.batchInsertClassTeacher",list);
    }

    @Override
    public void batchUpdateClassTeacher(List<ClassTeacher> list) {
        sqlSessionTemplate.update("ClassTeacherMapper.batchUpdateClassTeacher",list);
    }

    @Override
    public     Long  insertClassTeacher(ClassTeacher entity) {
        sqlSessionTemplate.insert("ClassTeacherMapper.insertClassTeacher",entity);
  return entity.getId();    }
    @Override
    public void updateClassTeacher(ClassTeacher entity) {
        sqlSessionTemplate.update("ClassTeacherMapper.updateClassTeacher", entity);
    }
    @Override
    public void updateClassTeacherWithoutNull(ClassTeacher entity) {
        sqlSessionTemplate.update("ClassTeacherMapper.updateClassTeacherWithoutNull", entity);
    }
    @Override
    public void updateClassTeacherByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("ClassTeacherMapper.updateClassTeacherByWhereSql", entity);
    }

    @Override
    public void truncateClassTeacher() {
        sqlSessionTemplate.delete("ClassTeacherMapper.truncateClassTeacher");
    }
    @Override
    public void deleteClassTeacher(Map<String, Object> query) {
        sqlSessionTemplate.delete("ClassTeacherMapper.deleteClassTeacher",query);
    }
    @Override
    public void deleteClassTeacherList(Map<String, Object> query) {
        sqlSessionTemplate.delete("ClassTeacherMapper.deleteClassTeacherList",query);
    }
    @Override
    public void deleteClassTeacherByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("ClassTeacherMapper.deleteClassTeacherByWhereSql",query);
    }
    @Override
    public void batchDeleteClassTeacher(List<Long> list) {
        sqlSessionTemplate.delete("ClassTeacherMapper.batchDeleteClassTeacher",list);
    }
    @Override
    public void batchDeleteClassTeacherList(List<ClassTeacher> list) {
        sqlSessionTemplate.delete("ClassTeacherMapper.batchDeleteClassTeacherList",list);
    }

    @Override
    public List<ClassTeacherMany> getManyList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("ClassTeacherMapper.getManyList",query);
    }

    @Override
    public Integer getManyListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ClassTeacherMapper.getManyListCount",query);
    }
}
