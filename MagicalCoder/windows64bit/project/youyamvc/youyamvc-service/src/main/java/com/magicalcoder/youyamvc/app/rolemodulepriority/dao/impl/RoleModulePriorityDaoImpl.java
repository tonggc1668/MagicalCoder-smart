package com.magicalcoder.youyamvc.app.rolemodulepriority.dao.impl;

import com.magicalcoder.youyamvc.app.rolemodulepriority.dao.RoleModulePriorityDao;
import com.magicalcoder.youyamvc.app.model.RoleModulePriority;
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
@Component("roleModulePriorityDao")
public class RoleModulePriorityDaoImpl   implements RoleModulePriorityDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public RoleModulePriority getRoleModulePriority(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("RoleModulePriorityMapper.getRoleModulePriority",query);
    }
    @Override
    public List<RoleModulePriority> getRoleModulePriorityList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("RoleModulePriorityMapper.getRoleModulePriorityList", query);
    }
    @Override
    public Integer getRoleModulePriorityListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("RoleModulePriorityMapper.getRoleModulePriorityListCount", query);
    }

    @Override
    public void batchInsertRoleModulePriority(List<RoleModulePriority> list) {
        sqlSessionTemplate.insert("RoleModulePriorityMapper.batchInsertRoleModulePriority",list);
    }

    @Override
    public void batchUpdateRoleModulePriority(List<RoleModulePriority> list) {
        sqlSessionTemplate.update("RoleModulePriorityMapper.batchUpdateRoleModulePriority",list);
    }

    @Override
    public     Long  insertRoleModulePriority(RoleModulePriority entity) {
        sqlSessionTemplate.insert("RoleModulePriorityMapper.insertRoleModulePriority",entity);
  return entity.getId();    }
    @Override
    public void updateRoleModulePriority(RoleModulePriority entity) {
        sqlSessionTemplate.update("RoleModulePriorityMapper.updateRoleModulePriority", entity);
    }
    @Override
    public void updateRoleModulePriorityWithoutNull(RoleModulePriority entity) {
        sqlSessionTemplate.update("RoleModulePriorityMapper.updateRoleModulePriorityWithoutNull", entity);
    }
    @Override
    public void updateRoleModulePriorityByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("RoleModulePriorityMapper.updateRoleModulePriorityByWhereSql", entity);
    }

    @Override
    public List<RoleModulePriority> getRoleModulePriorityOneToOneRelateList(Map<String, Object> query){
        return sqlSessionTemplate.selectList("RoleModulePriorityMapper.getRoleModulePriorityOneToOneRelateList", query);
    }
    @Override
    public Integer getRoleModulePriorityOneToOneRelateListCount(Map<String, Object> query){
        return sqlSessionTemplate.selectOne("RoleModulePriorityMapper.getRoleModulePriorityOneToOneRelateListCount", query);
    }
    @Override
    public void truncateRoleModulePriority() {
        sqlSessionTemplate.delete("RoleModulePriorityMapper.truncateRoleModulePriority");
    }
    @Override
    public void deleteRoleModulePriority(Map<String, Object> query) {
        sqlSessionTemplate.delete("RoleModulePriorityMapper.deleteRoleModulePriority",query);
    }
    @Override
    public void deleteRoleModulePriorityList(Map<String, Object> query) {
        sqlSessionTemplate.delete("RoleModulePriorityMapper.deleteRoleModulePriorityList",query);
    }
    @Override
    public void deleteRoleModulePriorityByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("RoleModulePriorityMapper.deleteRoleModulePriorityByWhereSql",query);
    }
    @Override
    public void batchDeleteRoleModulePriority(List<Long> list) {
        sqlSessionTemplate.delete("RoleModulePriorityMapper.batchDeleteRoleModulePriority",list);
    }
    @Override
    public void batchDeleteRoleModulePriorityList(List<RoleModulePriority> list) {
        sqlSessionTemplate.delete("RoleModulePriorityMapper.batchDeleteRoleModulePriorityList",list);
    }

}
