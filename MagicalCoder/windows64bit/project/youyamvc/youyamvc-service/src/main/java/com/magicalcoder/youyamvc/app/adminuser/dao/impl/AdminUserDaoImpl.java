package com.magicalcoder.youyamvc.app.adminuser.dao.impl;

import com.magicalcoder.youyamvc.app.adminuser.dao.AdminUserDao;
import com.magicalcoder.youyamvc.app.model.AdminUser;
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
@Component("adminUserDao")
public class AdminUserDaoImpl   implements AdminUserDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public AdminUser getAdminUser(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("AdminUserMapper.getAdminUser",query);
    }
    @Override
    public List<AdminUser> getAdminUserList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("AdminUserMapper.getAdminUserList", query);
    }
    @Override
    public Integer getAdminUserListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("AdminUserMapper.getAdminUserListCount", query);
    }

    @Override
    public void batchInsertAdminUser(List<AdminUser> list) {
        sqlSessionTemplate.insert("AdminUserMapper.batchInsertAdminUser",list);
    }

    @Override
    public void batchUpdateAdminUser(List<AdminUser> list) {
        sqlSessionTemplate.update("AdminUserMapper.batchUpdateAdminUser",list);
    }

    @Override
    public     Long  insertAdminUser(AdminUser entity) {
        sqlSessionTemplate.insert("AdminUserMapper.insertAdminUser",entity);
  return entity.getId();    }
    @Override
    public void updateAdminUser(AdminUser entity) {
        sqlSessionTemplate.update("AdminUserMapper.updateAdminUser", entity);
    }
    @Override
    public void updateAdminUserWithoutNull(AdminUser entity) {
        sqlSessionTemplate.update("AdminUserMapper.updateAdminUserWithoutNull", entity);
    }
    @Override
    public void updateAdminUserByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("AdminUserMapper.updateAdminUserByWhereSql", entity);
    }

    @Override
    public List<AdminUser> getAdminUserOneToOneRelateList(Map<String, Object> query){
        return sqlSessionTemplate.selectList("AdminUserMapper.getAdminUserOneToOneRelateList", query);
    }
    @Override
    public Integer getAdminUserOneToOneRelateListCount(Map<String, Object> query){
        return sqlSessionTemplate.selectOne("AdminUserMapper.getAdminUserOneToOneRelateListCount", query);
    }
    @Override
    public void truncateAdminUser() {
        sqlSessionTemplate.delete("AdminUserMapper.truncateAdminUser");
    }
    @Override
    public void deleteAdminUser(Map<String, Object> query) {
        sqlSessionTemplate.delete("AdminUserMapper.deleteAdminUser",query);
    }
    @Override
    public void deleteAdminUserList(Map<String, Object> query) {
        sqlSessionTemplate.delete("AdminUserMapper.deleteAdminUserList",query);
    }
    @Override
    public void deleteAdminUserByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("AdminUserMapper.deleteAdminUserByWhereSql",query);
    }
    @Override
    public void batchDeleteAdminUser(List<Long> list) {
        sqlSessionTemplate.delete("AdminUserMapper.batchDeleteAdminUser",list);
    }
    @Override
    public void batchDeleteAdminUserList(List<AdminUser> list) {
        sqlSessionTemplate.delete("AdminUserMapper.batchDeleteAdminUserList",list);
    }

}
