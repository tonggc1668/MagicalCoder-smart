package com.magicalcoder.youyamvc.app.classteacher.dao;

import com.magicalcoder.youyamvc.app.model.ClassTeacher;
import com.magicalcoder.youyamvc.app.model.defined.ClassTeacherMany;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface ClassTeacherDao{
    ClassTeacher getClassTeacher(Map<String, Object> query);
    List<ClassTeacher> getClassTeacherList(Map<String, Object> query);
    Integer getClassTeacherListCount(Map<String, Object> query);

    Long  insertClassTeacher(ClassTeacher entity);    void batchInsertClassTeacher(List<ClassTeacher> list);
    void batchUpdateClassTeacher(List<ClassTeacher> list);
    void updateClassTeacher(ClassTeacher entity);
    void updateClassTeacherWithoutNull(ClassTeacher entity);
    void updateClassTeacherByWhereSql(Map<String,Object> entity);

    void truncateClassTeacher();
    void deleteClassTeacher(Map<String, Object> query);
    void deleteClassTeacherList(Map<String, Object> query);
    void deleteClassTeacherByWhereSql(Map<String, Object> query);
    void batchDeleteClassTeacher(List<Long> list);
    void batchDeleteClassTeacherList(List<ClassTeacher> entityList);
    //relate
    List<ClassTeacherMany> getManyList(Map<String, Object> query);
    Integer getManyListCount(Map<String, Object> query);
}
