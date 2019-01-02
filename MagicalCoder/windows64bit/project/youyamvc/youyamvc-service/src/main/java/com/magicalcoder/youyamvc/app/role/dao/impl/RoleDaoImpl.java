package com.magicalcoder.youyamvc.app.role.dao.impl;

import com.magicalcoder.youyamvc.app.role.dao.RoleDao;
import com.magicalcoder.youyamvc.app.model.Role;
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
@Component("roleDao")
public class RoleDaoImpl   implements RoleDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public Role getRole(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("RoleMapper.getRole",query);
    }
    @Override
    public List<Role> getRoleList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("RoleMapper.getRoleList", query);
    }
    @Override
    public Integer getRoleListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("RoleMapper.getRoleListCount", query);
    }

    @Override
    public void batchInsertRole(List<Role> list) {
        sqlSessionTemplate.insert("RoleMapper.batchInsertRole",list);
    }

    @Override
    public void batchUpdateRole(List<Role> list) {
        sqlSessionTemplate.update("RoleMapper.batchUpdateRole",list);
    }

    @Override
    public     Long  insertRole(Role entity) {
        sqlSessionTemplate.insert("RoleMapper.insertRole",entity);
  return entity.getId();    }
    @Override
    public void updateRole(Role entity) {
        sqlSessionTemplate.update("RoleMapper.updateRole", entity);
    }
    @Override
    public void updateRoleWithoutNull(Role entity) {
        sqlSessionTemplate.update("RoleMapper.updateRoleWithoutNull", entity);
    }
    @Override
    public void updateRoleByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("RoleMapper.updateRoleByWhereSql", entity);
    }

    @Override
    public void truncateRole() {
        sqlSessionTemplate.delete("RoleMapper.truncateRole");
    }
    @Override
    public void deleteRole(Map<String, Object> query) {
        sqlSessionTemplate.delete("RoleMapper.deleteRole",query);
    }
    @Override
    public void deleteRoleList(Map<String, Object> query) {
        sqlSessionTemplate.delete("RoleMapper.deleteRoleList",query);
    }
    @Override
    public void deleteRoleByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("RoleMapper.deleteRoleByWhereSql",query);
    }
    @Override
    public void batchDeleteRole(List<Long> list) {
        sqlSessionTemplate.delete("RoleMapper.batchDeleteRole",list);
    }
    @Override
    public void batchDeleteRoleList(List<Role> list) {
        sqlSessionTemplate.delete("RoleMapper.batchDeleteRoleList",list);
    }

}
