
package com.magicalcoder.youyamvc.app.userweb.service.impl;

import com.magicalcoder.youyamvc.app.userweb.dao.UserWebDao;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebService;
import com.magicalcoder.youyamvc.app.model.UserWeb;
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
@Component("userWebService")
public class UserWebServiceImpl implements UserWebService{
    @Resource(name="userWebDao")
    private UserWebDao userWebDao;


    @Override
    public UserWeb getUserWeb(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return userWebDao.getUserWeb(query);
    }

    @Override
    public UserWeb selectOneUserWebWillThrowException(Map<String, Object> query){
        return userWebDao.getUserWeb(query);
    }

    @Override
    public List<UserWeb> getUserWebList(Map<String, Object> query) {
        return userWebDao.getUserWebList(query);
    }

    @Override
    public Integer getUserWebListCount(Map<String, Object> query) {
        return userWebDao.getUserWebListCount(query);
    }

    @Override
    public     Long  insertUserWeb(UserWeb entity) {
        return userWebDao.insertUserWeb(entity);
    }

    @Override
    public void updateUserWeb(UserWeb entity) {
        //校验
        userWebDao.updateUserWeb(entity);
    }

    @Override
    public void updateUserWebWithoutNull(UserWeb entity) {
        //校验
        userWebDao.updateUserWebWithoutNull(entity);
    }

    @Override
    public void updateUserWebByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        userWebDao.updateUserWebByWhereSql(entity);
    }

    @Override
    public void deleteUserWeb(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        userWebDao.deleteUserWeb(query);
    }
    @Override
    public void deleteUserWebList(Map<String,Object> entity){
        userWebDao.deleteUserWebList(entity);
    }

    @Override
    public void deleteUserWebByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        userWebDao.deleteUserWebByWhereSql(query);
    }


    @Override
    public void truncateUserWeb() {
        userWebDao.truncateUserWeb();
    }

    @Override
    public void batchInsertUserWeb(List<UserWeb> list) {
        //校验
        userWebDao.batchInsertUserWeb(list);
    }

    @Override
    public void batchUpdateUserWeb(List<UserWeb> list) {
        //校验
        userWebDao.batchUpdateUserWeb(list);
    }
    @Override
    public void batchDeleteUserWeb(List<Long> idList) {
        userWebDao.batchDeleteUserWeb(idList);
    }

    @Override
    public void batchDeleteUserWebList(List<UserWeb> entityList){
        userWebDao.batchDeleteUserWebList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<UserWeb> list) {
        if(list!=null && list.size()>0){
            for(UserWeb userWeb : list){
                if (userWeb.getId() == null) {
                    insertUserWeb(userWeb);
                } else {
                    UserWeb entity = getUserWeb(userWeb.getId());
                    if(entity==null){
                        insertUserWeb(userWeb);
                    }else {
                        CopyerSpringUtil.copyProperties(userWeb, entity);
                        updateUserWeb(entity);
                    }
                }
            }
        }
    }


    public List<UserWeb> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<UserWeb> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getUserWebList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("userNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("registTimeFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return new ArrayList<UserWeb>();
    }

    private List<UserWeb> searchList(String field,String keyword){
        List<UserWeb> list = getUserWebList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getUserWebList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
