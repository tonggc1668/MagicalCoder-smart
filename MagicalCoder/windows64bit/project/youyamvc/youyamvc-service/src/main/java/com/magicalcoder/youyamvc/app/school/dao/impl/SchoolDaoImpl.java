package com.magicalcoder.youyamvc.app.school.dao.impl;

import com.magicalcoder.youyamvc.app.school.dao.SchoolDao;
import com.magicalcoder.youyamvc.app.model.School;
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
@Component("schoolDao")
public class SchoolDaoImpl   implements SchoolDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public School getSchool(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("SchoolMapper.getSchool",query);
    }
    @Override
    public List<School> getSchoolList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("SchoolMapper.getSchoolList", query);
    }
    @Override
    public Integer getSchoolListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("SchoolMapper.getSchoolListCount", query);
    }

    @Override
    public void batchInsertSchool(List<School> list) {
        sqlSessionTemplate.insert("SchoolMapper.batchInsertSchool",list);
    }

    @Override
    public void batchUpdateSchool(List<School> list) {
        sqlSessionTemplate.update("SchoolMapper.batchUpdateSchool",list);
    }

    @Override
    public     Long  insertSchool(School entity) {
        sqlSessionTemplate.insert("SchoolMapper.insertSchool",entity);
  return entity.getId();    }
    @Override
    public void updateSchool(School entity) {
        sqlSessionTemplate.update("SchoolMapper.updateSchool", entity);
    }
    @Override
    public void updateSchoolWithoutNull(School entity) {
        sqlSessionTemplate.update("SchoolMapper.updateSchoolWithoutNull", entity);
    }
    @Override
    public void updateSchoolByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("SchoolMapper.updateSchoolByWhereSql", entity);
    }

    @Override
    public void truncateSchool() {
        sqlSessionTemplate.delete("SchoolMapper.truncateSchool");
    }
    @Override
    public void deleteSchool(Map<String, Object> query) {
        sqlSessionTemplate.delete("SchoolMapper.deleteSchool",query);
    }
    @Override
    public void deleteSchoolList(Map<String, Object> query) {
        sqlSessionTemplate.delete("SchoolMapper.deleteSchoolList",query);
    }
    @Override
    public void deleteSchoolByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("SchoolMapper.deleteSchoolByWhereSql",query);
    }
    @Override
    public void batchDeleteSchool(List<Long> list) {
        sqlSessionTemplate.delete("SchoolMapper.batchDeleteSchool",list);
    }
    @Override
    public void batchDeleteSchoolList(List<School> list) {
        sqlSessionTemplate.delete("SchoolMapper.batchDeleteSchoolList",list);
    }

}
