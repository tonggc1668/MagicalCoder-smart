
package com.magicalcoder.youyamvc.app.student.service.impl;

import com.magicalcoder.youyamvc.app.student.dao.StudentDao;
import com.magicalcoder.youyamvc.app.student.service.StudentService;
import com.magicalcoder.youyamvc.app.model.Student;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@Component("studentService")
public class StudentServiceImpl implements StudentService{
    @Resource(name="studentDao")
    private StudentDao studentDao;


    @Override
    public Student getStudent(Integer id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return studentDao.getStudent(query);
    }

    @Override
    public Student selectOneStudentWillThrowException(Map<String, Object> query){
        return studentDao.getStudent(query);
    }

    @Override
    public List<Student> getStudentList(Map<String, Object> query) {
        return studentDao.getStudentList(query);
    }

    @Override
    public Integer getStudentListCount(Map<String, Object> query) {
        return studentDao.getStudentListCount(query);
    }

    @Override
    public     Integer  insertStudent(Student entity) {
        return studentDao.insertStudent(entity);
    }

    @Override
    public void updateStudent(Student entity) {
        //校验
        studentDao.updateStudent(entity);
    }

    @Override
    public void updateStudentWithoutNull(Student entity) {
        //校验
        studentDao.updateStudentWithoutNull(entity);
    }

    @Override
    public void updateStudentByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        studentDao.updateStudentByWhereSql(entity);
    }

    @Override
    public void deleteStudent(Integer id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        studentDao.deleteStudent(query);
    }
    @Override
    public void deleteStudentList(Map<String,Object> entity){
        studentDao.deleteStudentList(entity);
    }

    @Override
    public void deleteStudentByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        studentDao.deleteStudentByWhereSql(query);
    }

    @Override
    public List<Student> getStudentOneToOneRelateList(Map<String, Object> query){
        return studentDao.getStudentOneToOneRelateList(query);
    }
    @Override
    public Integer getStudentOneToOneRelateListCount(Map<String, Object> query){
        return studentDao.getStudentOneToOneRelateListCount(query);
    }

    @Override
    public void truncateStudent() {
        studentDao.truncateStudent();
    }

    @Override
    public void batchInsertStudent(List<Student> list) {
        //校验
        studentDao.batchInsertStudent(list);
    }

    @Override
    public void batchUpdateStudent(List<Student> list) {
        //校验
        studentDao.batchUpdateStudent(list);
    }
    @Override
    public void batchDeleteStudent(List<Integer> idList) {
        studentDao.batchDeleteStudent(idList);
    }

    @Override
    public void batchDeleteStudentList(List<Student> entityList){
        studentDao.batchDeleteStudentList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<Student> list) {
        if(list!=null && list.size()>0){
            for(Student student : list){
                if (student.getId() == null) {
                    insertStudent(student);
                } else {
                    Student entity = getStudent(student.getId());
                    if(entity==null){
                        insertStudent(student);
                    }else {
                        CopyerSpringUtil.copyProperties(student, entity);
                        updateStudent(entity);
                    }
                }
            }
        }
    }


    public List<Student> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<Student> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getStudentList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("nameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("classIdFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("sexFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("adminUserIdFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
        }
        return new ArrayList<Student>();
    }

    private List<Student> searchList(String field,String keyword){
        List<Student> list = getStudentList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getStudentList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
