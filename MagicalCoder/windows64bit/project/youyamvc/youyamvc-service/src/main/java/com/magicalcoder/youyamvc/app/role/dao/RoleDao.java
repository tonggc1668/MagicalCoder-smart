package com.magicalcoder.youyamvc.app.role.dao;

import com.magicalcoder.youyamvc.app.model.Role;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface RoleDao{
    Role getRole(Map<String, Object> query);
    List<Role> getRoleList(Map<String, Object> query);
    Integer getRoleListCount(Map<String, Object> query);

    Long  insertRole(Role entity);    void batchInsertRole(List<Role> list);
    void batchUpdateRole(List<Role> list);
    void updateRole(Role entity);
    void updateRoleWithoutNull(Role entity);
    void updateRoleByWhereSql(Map<String,Object> entity);

    void truncateRole();
    void deleteRole(Map<String, Object> query);
    void deleteRoleList(Map<String, Object> query);
    void deleteRoleByWhereSql(Map<String, Object> query);
    void batchDeleteRole(List<Long> list);
    void batchDeleteRoleList(List<Role> entityList);
}
