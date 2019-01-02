package com.magicalcoder.youyamvc.app.student.dao.impl;

import com.magicalcoder.youyamvc.app.student.dao.StudentDao;
import com.magicalcoder.youyamvc.app.model.Student;
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
@Component("studentDao")
public class StudentDaoImpl   implements StudentDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public Student getStudent(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("StudentMapper.getStudent",query);
    }
    @Override
    public List<Student> getStudentList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("StudentMapper.getStudentList", query);
    }
    @Override
    public Integer getStudentListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("StudentMapper.getStudentListCount", query);
    }

    @Override
    public void batchInsertStudent(List<Student> list) {
        sqlSessionTemplate.insert("StudentMapper.batchInsertStudent",list);
    }

    @Override
    public void batchUpdateStudent(List<Student> list) {
        sqlSessionTemplate.update("StudentMapper.batchUpdateStudent",list);
    }

    @Override
    public     Integer  insertStudent(Student entity) {
        sqlSessionTemplate.insert("StudentMapper.insertStudent",entity);
  return entity.getId();    }
    @Override
    public void updateStudent(Student entity) {
        sqlSessionTemplate.update("StudentMapper.updateStudent", entity);
    }
    @Override
    public void updateStudentWithoutNull(Student entity) {
        sqlSessionTemplate.update("StudentMapper.updateStudentWithoutNull", entity);
    }
    @Override
    public void updateStudentByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("StudentMapper.updateStudentByWhereSql", entity);
    }

    @Override
    public List<Student> getStudentOneToOneRelateList(Map<String, Object> query){
        return sqlSessionTemplate.selectList("StudentMapper.getStudentOneToOneRelateList", query);
    }
    @Override
    public Integer getStudentOneToOneRelateListCount(Map<String, Object> query){
        return sqlSessionTemplate.selectOne("StudentMapper.getStudentOneToOneRelateListCount", query);
    }
    @Override
    public void truncateStudent() {
        sqlSessionTemplate.delete("StudentMapper.truncateStudent");
    }
    @Override
    public void deleteStudent(Map<String, Object> query) {
        sqlSessionTemplate.delete("StudentMapper.deleteStudent",query);
    }
    @Override
    public void deleteStudentList(Map<String, Object> query) {
        sqlSessionTemplate.delete("StudentMapper.deleteStudentList",query);
    }
    @Override
    public void deleteStudentByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("StudentMapper.deleteStudentByWhereSql",query);
    }
    @Override
    public void batchDeleteStudent(List<Integer> list) {
        sqlSessionTemplate.delete("StudentMapper.batchDeleteStudent",list);
    }
    @Override
    public void batchDeleteStudentList(List<Student> list) {
        sqlSessionTemplate.delete("StudentMapper.batchDeleteStudentList",list);
    }

}
