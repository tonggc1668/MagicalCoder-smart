package com.magicalcoder.youyamvc.app.userweb.dao.impl;

import com.magicalcoder.youyamvc.app.userweb.dao.UserWebDao;
import com.magicalcoder.youyamvc.app.model.UserWeb;
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
@Component("userWebDao")
public class UserWebDaoImpl   implements UserWebDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public UserWeb getUserWeb(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("UserWebMapper.getUserWeb",query);
    }
    @Override
    public List<UserWeb> getUserWebList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("UserWebMapper.getUserWebList", query);
    }
    @Override
    public Integer getUserWebListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("UserWebMapper.getUserWebListCount", query);
    }

    @Override
    public void batchInsertUserWeb(List<UserWeb> list) {
        sqlSessionTemplate.insert("UserWebMapper.batchInsertUserWeb",list);
    }

    @Override
    public void batchUpdateUserWeb(List<UserWeb> list) {
        sqlSessionTemplate.update("UserWebMapper.batchUpdateUserWeb",list);
    }

    @Override
    public     Long  insertUserWeb(UserWeb entity) {
        sqlSessionTemplate.insert("UserWebMapper.insertUserWeb",entity);
  return entity.getId();    }
    @Override
    public void updateUserWeb(UserWeb entity) {
        sqlSessionTemplate.update("UserWebMapper.updateUserWeb", entity);
    }
    @Override
    public void updateUserWebWithoutNull(UserWeb entity) {
        sqlSessionTemplate.update("UserWebMapper.updateUserWebWithoutNull", entity);
    }
    @Override
    public void updateUserWebByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("UserWebMapper.updateUserWebByWhereSql", entity);
    }

    @Override
    public void truncateUserWeb() {
        sqlSessionTemplate.delete("UserWebMapper.truncateUserWeb");
    }
    @Override
    public void deleteUserWeb(Map<String, Object> query) {
        sqlSessionTemplate.delete("UserWebMapper.deleteUserWeb",query);
    }
    @Override
    public void deleteUserWebList(Map<String, Object> query) {
        sqlSessionTemplate.delete("UserWebMapper.deleteUserWebList",query);
    }
    @Override
    public void deleteUserWebByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("UserWebMapper.deleteUserWebByWhereSql",query);
    }
    @Override
    public void batchDeleteUserWeb(List<Long> list) {
        sqlSessionTemplate.delete("UserWebMapper.batchDeleteUserWeb",list);
    }
    @Override
    public void batchDeleteUserWebList(List<UserWeb> list) {
        sqlSessionTemplate.delete("UserWebMapper.batchDeleteUserWebList",list);
    }

}
