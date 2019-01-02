
package com.magicalcoder.youyamvc.app.role.service.impl;

import com.magicalcoder.youyamvc.app.role.dao.RoleDao;
import com.magicalcoder.youyamvc.app.role.service.RoleService;
import com.magicalcoder.youyamvc.app.model.Role;
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
@Component("roleService")
public class RoleServiceImpl implements RoleService{
    @Resource(name="roleDao")
    private RoleDao roleDao;


    @Override
    public Role getRole(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return roleDao.getRole(query);
    }

    @Override
    public Role selectOneRoleWillThrowException(Map<String, Object> query){
        return roleDao.getRole(query);
    }

    @Override
    public List<Role> getRoleList(Map<String, Object> query) {
        return roleDao.getRoleList(query);
    }

    @Override
    public Integer getRoleListCount(Map<String, Object> query) {
        return roleDao.getRoleListCount(query);
    }

    @Override
    public     Long  insertRole(Role entity) {
        return roleDao.insertRole(entity);
    }

    @Override
    public void updateRole(Role entity) {
        //校验
        roleDao.updateRole(entity);
    }

    @Override
    public void updateRoleWithoutNull(Role entity) {
        //校验
        roleDao.updateRoleWithoutNull(entity);
    }

    @Override
    public void updateRoleByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        roleDao.updateRoleByWhereSql(entity);
    }

    @Override
    public void deleteRole(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        roleDao.deleteRole(query);
    }
    @Override
    public void deleteRoleList(Map<String,Object> entity){
        roleDao.deleteRoleList(entity);
    }

    @Override
    public void deleteRoleByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        roleDao.deleteRoleByWhereSql(query);
    }


    @Override
    public void truncateRole() {
        roleDao.truncateRole();
    }

    @Override
    public void batchInsertRole(List<Role> list) {
        //校验
        roleDao.batchInsertRole(list);
    }

    @Override
    public void batchUpdateRole(List<Role> list) {
        //校验
        roleDao.batchUpdateRole(list);
    }
    @Override
    public void batchDeleteRole(List<Long> idList) {
        roleDao.batchDeleteRole(idList);
    }

    @Override
    public void batchDeleteRoleList(List<Role> entityList){
        roleDao.batchDeleteRoleList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<Role> list) {
        if(list!=null && list.size()>0){
            for(Role role : list){
                if (role.getId() == null) {
                    insertRole(role);
                } else {
                    Role entity = getRole(role.getId());
                    if(entity==null){
                        insertRole(role);
                    }else {
                        CopyerSpringUtil.copyProperties(role, entity);
                        updateRole(entity);
                    }
                }
            }
        }
    }


    public List<Role> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<Role> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getRoleList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("roleNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return new ArrayList<Role>();
    }

    private List<Role> searchList(String field,String keyword){
        List<Role> list = getRoleList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getRoleList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
