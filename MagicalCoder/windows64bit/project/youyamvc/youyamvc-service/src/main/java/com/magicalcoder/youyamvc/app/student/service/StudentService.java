package com.magicalcoder.youyamvc.app.student.service;

import com.magicalcoder.youyamvc.app.model.Student;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface StudentService{

    /**
    * 根据主键获取实体
    * @param id 主键
    * @return
    */
    Student getStudent(Integer id);

    /**
    * 调用mybatis selectOne 如果查询返回超过1条 就会发生异常 请自行处理
    * @param query 入参 调用MapUtil构造
    * @return
    */
    Student selectOneStudentWillThrowException(Map<String, Object> query);

    /**
    * 查询实体集合
    * @param query
    * @return
    */
    List<Student> getStudentList(Map<String, Object> query);

    /**
    * 查询实体集合的个数
    * @param query
    * @return
    */
    Integer getStudentListCount(Map<String, Object> query);

    /**
    * 保存实体 保证entity 主键为空
    * @param entity
    * @return
    */
    Integer  insertStudent(Student entity);
    /**
    * 更新实体 保证实体中的主键不为空
    * @param entity
    */
    void updateStudent(Student entity);
    /**
    * 更新实体 null字段忽略更新
    * @param entity
    */
    void updateStudentWithoutNull(Student entity);
    /**
    * 更新实体 自定义条件
    * @param entity   not empty
    * @param whereSql not blank 有sql注入风险 请开发人员自行保证安全性
    */
    void updateStudentByWhereSql(Map<String,Object> entity,String whereSql);

    /**
    * 根据主键删除实体
    * @param id
    */
    void deleteStudent(Integer id);
    /**
    * 删除实体集合
    * @param entity 如果字段值不为空 将作为查询条件
    */
    void deleteStudentList(Map<String,Object> entity);

    /**
    * 根据自定义条件删除实体
    * @param whereSql not blank  有sql注入风险 请开发人员自行保证安全性
    */
    void deleteStudentByWhereSql(String whereSql);

    /**
    * 根据外键表的条件查询本实体
    * @query
    */
    List<Student> getStudentOneToOneRelateList(Map<String, Object> query);

    /**
    * 根据外键表的条件查询本实体个数
    * @query
    */
    Integer getStudentOneToOneRelateListCount(Map<String, Object> query);

    /**
    * 清空实体表
    */
    void truncateStudent();

    /**
    *  批量保存实体，保证list中的实体 主键为空
    * @param list
    */
    void batchInsertStudent(List<Student> list);

    /**
    * 批量更新实体 保证list中的实体 主键不为空
    * @param list
    */
    void batchUpdateStudent(List<Student> list);
    /**
    * 根据主键list 批量删除实体
    * @param idList
    */
    void batchDeleteStudent(List<Integer> idList);
    /**
    * 根据主键list 批量删除实体
    * @param entityList 如果字段值不为空 将作为查询条件
    */
    void batchDeleteStudentList(List<Student> entityList);

    /**
    * 事务保证 导入json文件成功
    * @param list json反序列化后的文件
    * @return
    */
    void transactionImportJsonList(List<Student> list);


    /**
    *
    *尝试获取列表 一个个字段进行尝试查询 直到有结果返还
    */
    List<Student> tryQueryList(String keyword,String selectValue, String foreignJavaField);
}
