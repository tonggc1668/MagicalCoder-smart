package com.magicalcoder.youyamvc.app.student.dao;

import com.magicalcoder.youyamvc.app.model.Student;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface StudentDao{
    Student getStudent(Map<String, Object> query);
    List<Student> getStudentList(Map<String, Object> query);
    Integer getStudentListCount(Map<String, Object> query);

    Integer  insertStudent(Student entity);    void batchInsertStudent(List<Student> list);
    void batchUpdateStudent(List<Student> list);
    void updateStudent(Student entity);
    void updateStudentWithoutNull(Student entity);
    void updateStudentByWhereSql(Map<String,Object> entity);

    //oneToOne
    List<Student> getStudentOneToOneRelateList(Map<String, Object> query);
    Integer getStudentOneToOneRelateListCount(Map<String, Object> query);
    void truncateStudent();
    void deleteStudent(Map<String, Object> query);
    void deleteStudentList(Map<String, Object> query);
    void deleteStudentByWhereSql(Map<String, Object> query);
    void batchDeleteStudent(List<Integer> list);
    void batchDeleteStudentList(List<Student> entityList);
}
