package com.magicalcoder.youyamvc.app.classes.dao;

import com.magicalcoder.youyamvc.app.model.Classes;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface ClassesDao{
    Classes getClasses(Map<String, Object> query);
    List<Classes> getClassesList(Map<String, Object> query);
    Integer getClassesListCount(Map<String, Object> query);

    Long  insertClasses(Classes entity);    void batchInsertClasses(List<Classes> list);
    void batchUpdateClasses(List<Classes> list);
    void updateClasses(Classes entity);
    void updateClassesWithoutNull(Classes entity);
    void updateClassesByWhereSql(Map<String,Object> entity);

    //oneToOne
    List<Classes> getClassesOneToOneRelateList(Map<String, Object> query);
    Integer getClassesOneToOneRelateListCount(Map<String, Object> query);
    void truncateClasses();
    void deleteClasses(Map<String, Object> query);
    void deleteClassesList(Map<String, Object> query);
    void deleteClassesByWhereSql(Map<String, Object> query);
    void batchDeleteClasses(List<Long> list);
    void batchDeleteClassesList(List<Classes> entityList);
}
