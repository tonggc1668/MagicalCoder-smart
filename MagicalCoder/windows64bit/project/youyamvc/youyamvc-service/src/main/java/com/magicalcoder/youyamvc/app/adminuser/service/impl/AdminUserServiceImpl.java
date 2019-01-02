
package com.magicalcoder.youyamvc.app.adminuser.service.impl;

import com.magicalcoder.youyamvc.app.adminuser.dao.AdminUserDao;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
import com.magicalcoder.youyamvc.app.model.AdminUser;
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
@Component("adminUserService")
public class AdminUserServiceImpl implements AdminUserService{
    @Resource(name="adminUserDao")
    private AdminUserDao adminUserDao;


    @Override
    public AdminUser getAdminUser(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return adminUserDao.getAdminUser(query);
    }

    @Override
    public AdminUser selectOneAdminUserWillThrowException(Map<String, Object> query){
        return adminUserDao.getAdminUser(query);
    }

    @Override
    public List<AdminUser> getAdminUserList(Map<String, Object> query) {
        return adminUserDao.getAdminUserList(query);
    }

    @Override
    public Integer getAdminUserListCount(Map<String, Object> query) {
        return adminUserDao.getAdminUserListCount(query);
    }

    @Override
    public     Long  insertAdminUser(AdminUser entity) {
        return adminUserDao.insertAdminUser(entity);
    }

    @Override
    public void updateAdminUser(AdminUser entity) {
        //校验
        adminUserDao.updateAdminUser(entity);
    }

    @Override
    public void updateAdminUserWithoutNull(AdminUser entity) {
        //校验
        adminUserDao.updateAdminUserWithoutNull(entity);
    }

    @Override
    public void updateAdminUserByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        adminUserDao.updateAdminUserByWhereSql(entity);
    }

    @Override
    public void deleteAdminUser(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        adminUserDao.deleteAdminUser(query);
    }
    @Override
    public void deleteAdminUserList(Map<String,Object> entity){
        adminUserDao.deleteAdminUserList(entity);
    }

    @Override
    public void deleteAdminUserByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        adminUserDao.deleteAdminUserByWhereSql(query);
    }

    @Override
    public List<AdminUser> getAdminUserOneToOneRelateList(Map<String, Object> query){
        return adminUserDao.getAdminUserOneToOneRelateList(query);
    }
    @Override
    public Integer getAdminUserOneToOneRelateListCount(Map<String, Object> query){
        return adminUserDao.getAdminUserOneToOneRelateListCount(query);
    }

    @Override
    public void truncateAdminUser() {
        adminUserDao.truncateAdminUser();
    }

    @Override
    public void batchInsertAdminUser(List<AdminUser> list) {
        //校验
        adminUserDao.batchInsertAdminUser(list);
    }

    @Override
    public void batchUpdateAdminUser(List<AdminUser> list) {
        //校验
        adminUserDao.batchUpdateAdminUser(list);
    }
    @Override
    public void batchDeleteAdminUser(List<Long> idList) {
        adminUserDao.batchDeleteAdminUser(idList);
    }

    @Override
    public void batchDeleteAdminUserList(List<AdminUser> entityList){
        adminUserDao.batchDeleteAdminUserList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<AdminUser> list) {
        if(list!=null && list.size()>0){
            for(AdminUser adminUser : list){
                if (adminUser.getId() == null) {
                    insertAdminUser(adminUser);
                } else {
                    AdminUser entity = getAdminUser(adminUser.getId());
                    if(entity==null){
                        insertAdminUser(adminUser);
                    }else {
                        CopyerSpringUtil.copyProperties(adminUser, entity);
                        updateAdminUser(entity);
                    }
                }
            }
        }
    }


    public List<AdminUser> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<AdminUser> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getAdminUserList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            if(ProjectUtil.isNum(keyword)){
            list = searchList("idFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            list = searchList("userNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("realNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("emailFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("createTimeFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("updateTimeFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("superAdminFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("roleIdFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
        }
        return new ArrayList<AdminUser>();
    }

    private List<AdminUser> searchList(String field,String keyword){
        List<AdminUser> list = getAdminUserList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getAdminUserList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
