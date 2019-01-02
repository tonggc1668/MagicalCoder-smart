package com.magicalcoder.youyamvc.app.rolemodulepriority.dao;

import com.magicalcoder.youyamvc.app.model.RoleModulePriority;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface RoleModulePriorityDao{
    RoleModulePriority getRoleModulePriority(Map<String, Object> query);
    List<RoleModulePriority> getRoleModulePriorityList(Map<String, Object> query);
    Integer getRoleModulePriorityListCount(Map<String, Object> query);

    Long  insertRoleModulePriority(RoleModulePriority entity);    void batchInsertRoleModulePriority(List<RoleModulePriority> list);
    void batchUpdateRoleModulePriority(List<RoleModulePriority> list);
    void updateRoleModulePriority(RoleModulePriority entity);
    void updateRoleModulePriorityWithoutNull(RoleModulePriority entity);
    void updateRoleModulePriorityByWhereSql(Map<String,Object> entity);

    //oneToOne
    List<RoleModulePriority> getRoleModulePriorityOneToOneRelateList(Map<String, Object> query);
    Integer getRoleModulePriorityOneToOneRelateListCount(Map<String, Object> query);
    void truncateRoleModulePriority();
    void deleteRoleModulePriority(Map<String, Object> query);
    void deleteRoleModulePriorityList(Map<String, Object> query);
    void deleteRoleModulePriorityByWhereSql(Map<String, Object> query);
    void batchDeleteRoleModulePriority(List<Long> list);
    void batchDeleteRoleModulePriorityList(List<RoleModulePriority> entityList);
}
