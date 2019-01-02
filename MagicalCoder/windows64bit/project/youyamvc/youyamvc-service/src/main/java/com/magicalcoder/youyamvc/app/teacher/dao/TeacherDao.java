package com.magicalcoder.youyamvc.app.teacher.dao;

import com.magicalcoder.youyamvc.app.model.Teacher;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface TeacherDao{
    Teacher getTeacher(Map<String, Object> query);
    List<Teacher> getTeacherList(Map<String, Object> query);
    Integer getTeacherListCount(Map<String, Object> query);

    Long  insertTeacher(Teacher entity);    void batchInsertTeacher(List<Teacher> list);
    void batchUpdateTeacher(List<Teacher> list);
    void updateTeacher(Teacher entity);
    void updateTeacherWithoutNull(Teacher entity);
    void updateTeacherByWhereSql(Map<String,Object> entity);

    void truncateTeacher();
    void deleteTeacher(Map<String, Object> query);
    void deleteTeacherList(Map<String, Object> query);
    void deleteTeacherByWhereSql(Map<String, Object> query);
    void batchDeleteTeacher(List<Long> list);
    void batchDeleteTeacherList(List<Teacher> entityList);
}
