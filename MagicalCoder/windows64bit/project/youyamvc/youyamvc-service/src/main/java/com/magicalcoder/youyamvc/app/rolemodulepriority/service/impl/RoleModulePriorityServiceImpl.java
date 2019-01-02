
package com.magicalcoder.youyamvc.app.rolemodulepriority.service.impl;

import com.magicalcoder.youyamvc.app.rolemodulepriority.dao.RoleModulePriorityDao;
import com.magicalcoder.youyamvc.app.rolemodulepriority.service.RoleModulePriorityService;
import com.magicalcoder.youyamvc.app.model.RoleModulePriority;
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
@Component("roleModulePriorityService")
public class RoleModulePriorityServiceImpl implements RoleModulePriorityService{
    @Resource(name="roleModulePriorityDao")
    private RoleModulePriorityDao roleModulePriorityDao;


    @Override
    public RoleModulePriority getRoleModulePriority(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return roleModulePriorityDao.getRoleModulePriority(query);
    }

    @Override
    public RoleModulePriority selectOneRoleModulePriorityWillThrowException(Map<String, Object> query){
        return roleModulePriorityDao.getRoleModulePriority(query);
    }

    @Override
    public List<RoleModulePriority> getRoleModulePriorityList(Map<String, Object> query) {
        return roleModulePriorityDao.getRoleModulePriorityList(query);
    }

    @Override
    public Integer getRoleModulePriorityListCount(Map<String, Object> query) {
        return roleModulePriorityDao.getRoleModulePriorityListCount(query);
    }

    @Override
    public     Long  insertRoleModulePriority(RoleModulePriority entity) {
        return roleModulePriorityDao.insertRoleModulePriority(entity);
    }

    @Override
    public void updateRoleModulePriority(RoleModulePriority entity) {
        //校验
        roleModulePriorityDao.updateRoleModulePriority(entity);
    }

    @Override
    public void updateRoleModulePriorityWithoutNull(RoleModulePriority entity) {
        //校验
        roleModulePriorityDao.updateRoleModulePriorityWithoutNull(entity);
    }

    @Override
    public void updateRoleModulePriorityByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        roleModulePriorityDao.updateRoleModulePriorityByWhereSql(entity);
    }

    @Override
    public void deleteRoleModulePriority(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        roleModulePriorityDao.deleteRoleModulePriority(query);
    }
    @Override
    public void deleteRoleModulePriorityList(Map<String,Object> entity){
        roleModulePriorityDao.deleteRoleModulePriorityList(entity);
    }

    @Override
    public void deleteRoleModulePriorityByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        roleModulePriorityDao.deleteRoleModulePriorityByWhereSql(query);
    }

    @Override
    public List<RoleModulePriority> getRoleModulePriorityOneToOneRelateList(Map<String, Object> query){
        return roleModulePriorityDao.getRoleModulePriorityOneToOneRelateList(query);
    }
    @Override
    public Integer getRoleModulePriorityOneToOneRelateListCount(Map<String, Object> query){
        return roleModulePriorityDao.getRoleModulePriorityOneToOneRelateListCount(query);
    }

    @Override
    public void truncateRoleModulePriority() {
        roleModulePriorityDao.truncateRoleModulePriority();
    }

    @Override
    public void batchInsertRoleModulePriority(List<RoleModulePriority> list) {
        //校验
        roleModulePriorityDao.batchInsertRoleModulePriority(list);
    }

    @Override
    public void batchUpdateRoleModulePriority(List<RoleModulePriority> list) {
        //校验
        roleModulePriorityDao.batchUpdateRoleModulePriority(list);
    }
    @Override
    public void batchDeleteRoleModulePriority(List<Long> idList) {
        roleModulePriorityDao.batchDeleteRoleModulePriority(idList);
    }

    @Override
    public void batchDeleteRoleModulePriorityList(List<RoleModulePriority> entityList){
        roleModulePriorityDao.batchDeleteRoleModulePriorityList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<RoleModulePriority> list) {
        if(list!=null && list.size()>0){
            for(RoleModulePriority roleModulePriority : list){
                if (roleModulePriority.getId() == null) {
                    insertRoleModulePriority(roleModulePriority);
                } else {
                    RoleModulePriority entity = getRoleModulePriority(roleModulePriority.getId());
                    if(entity==null){
                        insertRoleModulePriority(roleModulePriority);
                    }else {
                        CopyerSpringUtil.copyProperties(roleModulePriority, entity);
                        updateRoleModulePriority(entity);
                    }
                }
            }
        }
    }


    public List<RoleModulePriority> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<RoleModulePriority> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getRoleModulePriorityList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            if(ProjectUtil.isNum(keyword)){
            list = searchList("roleIdFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("moduleIdFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("priorityIdFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
        }
        return new ArrayList<RoleModulePriority>();
    }

    private List<RoleModulePriority> searchList(String field,String keyword){
        List<RoleModulePriority> list = getRoleModulePriorityList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getRoleModulePriorityList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
