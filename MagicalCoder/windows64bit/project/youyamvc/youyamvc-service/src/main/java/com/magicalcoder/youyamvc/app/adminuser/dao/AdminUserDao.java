package com.magicalcoder.youyamvc.app.adminuser.dao;

import com.magicalcoder.youyamvc.app.model.AdminUser;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface AdminUserDao{
    AdminUser getAdminUser(Map<String, Object> query);
    List<AdminUser> getAdminUserList(Map<String, Object> query);
    Integer getAdminUserListCount(Map<String, Object> query);

    Long  insertAdminUser(AdminUser entity);    void batchInsertAdminUser(List<AdminUser> list);
    void batchUpdateAdminUser(List<AdminUser> list);
    void updateAdminUser(AdminUser entity);
    void updateAdminUserWithoutNull(AdminUser entity);
    void updateAdminUserByWhereSql(Map<String,Object> entity);

    //oneToOne
    List<AdminUser> getAdminUserOneToOneRelateList(Map<String, Object> query);
    Integer getAdminUserOneToOneRelateListCount(Map<String, Object> query);
    void truncateAdminUser();
    void deleteAdminUser(Map<String, Object> query);
    void deleteAdminUserList(Map<String, Object> query);
    void deleteAdminUserByWhereSql(Map<String, Object> query);
    void batchDeleteAdminUser(List<Long> list);
    void batchDeleteAdminUserList(List<AdminUser> entityList);
}
