package com.magicalcoder.youyamvc.app.classteacher.service;

import com.magicalcoder.youyamvc.app.model.ClassTeacher;
import com.magicalcoder.youyamvc.app.model.defined.ClassTeacherMany;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface ClassTeacherService{

    /**
    * 根据主键获取实体
    * @param id 主键
    * @return
    */
    ClassTeacher getClassTeacher(Long id);

    /**
    * 调用mybatis selectOne 如果查询返回超过1条 就会发生异常 请自行处理
    * @param query 入参 调用MapUtil构造
    * @return
    */
    ClassTeacher selectOneClassTeacherWillThrowException(Map<String, Object> query);

    /**
    * 查询实体集合
    * @param query
    * @return
    */
    List<ClassTeacher> getClassTeacherList(Map<String, Object> query);

    /**
    * 查询实体集合的个数
    * @param query
    * @return
    */
    Integer getClassTeacherListCount(Map<String, Object> query);

    /**
    * 保存实体 保证entity 主键为空
    * @param entity
    * @return
    */
    Long  insertClassTeacher(ClassTeacher entity);
    /**
    * 更新实体 保证实体中的主键不为空
    * @param entity
    */
    void updateClassTeacher(ClassTeacher entity);
    /**
    * 更新实体 null字段忽略更新
    * @param entity
    */
    void updateClassTeacherWithoutNull(ClassTeacher entity);
    /**
    * 更新实体 自定义条件
    * @param entity   not empty
    * @param whereSql not blank 有sql注入风险 请开发人员自行保证安全性
    */
    void updateClassTeacherByWhereSql(Map<String,Object> entity,String whereSql);

    /**
    * 根据主键删除实体
    * @param id
    */
    void deleteClassTeacher(Long id);
    /**
    * 删除实体集合
    * @param entity 如果字段值不为空 将作为查询条件
    */
    void deleteClassTeacherList(Map<String,Object> entity);

    /**
    * 根据自定义条件删除实体
    * @param whereSql not blank  有sql注入风险 请开发人员自行保证安全性
    */
    void deleteClassTeacherByWhereSql(String whereSql);


    /**
    * 清空实体表
    */
    void truncateClassTeacher();

    /**
    *  批量保存实体，保证list中的实体 主键为空
    * @param list
    */
    void batchInsertClassTeacher(List<ClassTeacher> list);

    /**
    * 批量更新实体 保证list中的实体 主键不为空
    * @param list
    */
    void batchUpdateClassTeacher(List<ClassTeacher> list);
    /**
    * 根据主键list 批量删除实体
    * @param idList
    */
    void batchDeleteClassTeacher(List<Long> idList);
    /**
    * 根据主键list 批量删除实体
    * @param entityList 如果字段值不为空 将作为查询条件
    */
    void batchDeleteClassTeacherList(List<ClassTeacher> entityList);

    /**
    * 一对多关联详情页
    * @param query
    * @return
    */
    List<ClassTeacherMany> getManyList(Map<String, Object> query);
    /**
    * 一对多关联详情页个数
    * @param query
    * @return
    */
    Integer getManyListCount(Map<String, Object> query);
    /**
    * 事务保证 导入json文件成功
    * @param list json反序列化后的文件
    * @return
    */
    void transactionImportJsonList(List<ClassTeacher> list);


    /**
    *
    *尝试获取列表 一个个字段进行尝试查询 直到有结果返还
    */
    List<ClassTeacher> tryQueryList(String keyword,String selectValue, String foreignJavaField);
}
