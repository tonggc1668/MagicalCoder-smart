package com.magicalcoder.youyamvc.app.teacher.dao.impl;

import com.magicalcoder.youyamvc.app.teacher.dao.TeacherDao;
import com.magicalcoder.youyamvc.app.model.Teacher;
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
@Component("teacherDao")
public class TeacherDaoImpl   implements TeacherDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public Teacher getTeacher(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("TeacherMapper.getTeacher",query);
    }
    @Override
    public List<Teacher> getTeacherList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("TeacherMapper.getTeacherList", query);
    }
    @Override
    public Integer getTeacherListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("TeacherMapper.getTeacherListCount", query);
    }

    @Override
    public void batchInsertTeacher(List<Teacher> list) {
        sqlSessionTemplate.insert("TeacherMapper.batchInsertTeacher",list);
    }

    @Override
    public void batchUpdateTeacher(List<Teacher> list) {
        sqlSessionTemplate.update("TeacherMapper.batchUpdateTeacher",list);
    }

    @Override
    public     Long  insertTeacher(Teacher entity) {
        sqlSessionTemplate.insert("TeacherMapper.insertTeacher",entity);
  return entity.getId();    }
    @Override
    public void updateTeacher(Teacher entity) {
        sqlSessionTemplate.update("TeacherMapper.updateTeacher", entity);
    }
    @Override
    public void updateTeacherWithoutNull(Teacher entity) {
        sqlSessionTemplate.update("TeacherMapper.updateTeacherWithoutNull", entity);
    }
    @Override
    public void updateTeacherByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("TeacherMapper.updateTeacherByWhereSql", entity);
    }

    @Override
    public void truncateTeacher() {
        sqlSessionTemplate.delete("TeacherMapper.truncateTeacher");
    }
    @Override
    public void deleteTeacher(Map<String, Object> query) {
        sqlSessionTemplate.delete("TeacherMapper.deleteTeacher",query);
    }
    @Override
    public void deleteTeacherList(Map<String, Object> query) {
        sqlSessionTemplate.delete("TeacherMapper.deleteTeacherList",query);
    }
    @Override
    public void deleteTeacherByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("TeacherMapper.deleteTeacherByWhereSql",query);
    }
    @Override
    public void batchDeleteTeacher(List<Long> list) {
        sqlSessionTemplate.delete("TeacherMapper.batchDeleteTeacher",list);
    }
    @Override
    public void batchDeleteTeacherList(List<Teacher> list) {
        sqlSessionTemplate.delete("TeacherMapper.batchDeleteTeacherList",list);
    }

}
