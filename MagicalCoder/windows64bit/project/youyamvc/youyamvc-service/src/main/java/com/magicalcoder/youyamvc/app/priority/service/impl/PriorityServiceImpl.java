
package com.magicalcoder.youyamvc.app.priority.service.impl;

import com.magicalcoder.youyamvc.app.priority.dao.PriorityDao;
import com.magicalcoder.youyamvc.app.priority.service.PriorityService;
import com.magicalcoder.youyamvc.app.model.Priority;
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
@Component("priorityService")
public class PriorityServiceImpl implements PriorityService{
    @Resource(name="priorityDao")
    private PriorityDao priorityDao;


    @Override
    public Priority getPriority(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return priorityDao.getPriority(query);
    }

    @Override
    public Priority selectOnePriorityWillThrowException(Map<String, Object> query){
        return priorityDao.getPriority(query);
    }

    @Override
    public List<Priority> getPriorityList(Map<String, Object> query) {
        return priorityDao.getPriorityList(query);
    }

    @Override
    public Integer getPriorityListCount(Map<String, Object> query) {
        return priorityDao.getPriorityListCount(query);
    }

    @Override
    public     Long  insertPriority(Priority entity) {
        return priorityDao.insertPriority(entity);
    }

    @Override
    public void updatePriority(Priority entity) {
        //校验
        priorityDao.updatePriority(entity);
    }

    @Override
    public void updatePriorityWithoutNull(Priority entity) {
        //校验
        priorityDao.updatePriorityWithoutNull(entity);
    }

    @Override
    public void updatePriorityByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        priorityDao.updatePriorityByWhereSql(entity);
    }

    @Override
    public void deletePriority(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        priorityDao.deletePriority(query);
    }
    @Override
    public void deletePriorityList(Map<String,Object> entity){
        priorityDao.deletePriorityList(entity);
    }

    @Override
    public void deletePriorityByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        priorityDao.deletePriorityByWhereSql(query);
    }


    @Override
    public void truncatePriority() {
        priorityDao.truncatePriority();
    }

    @Override
    public void batchInsertPriority(List<Priority> list) {
        //校验
        priorityDao.batchInsertPriority(list);
    }

    @Override
    public void batchUpdatePriority(List<Priority> list) {
        //校验
        priorityDao.batchUpdatePriority(list);
    }
    @Override
    public void batchDeletePriority(List<Long> idList) {
        priorityDao.batchDeletePriority(idList);
    }

    @Override
    public void batchDeletePriorityList(List<Priority> entityList){
        priorityDao.batchDeletePriorityList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<Priority> list) {
        if(list!=null && list.size()>0){
            for(Priority priority : list){
                if (priority.getId() == null) {
                    insertPriority(priority);
                } else {
                    Priority entity = getPriority(priority.getId());
                    if(entity==null){
                        insertPriority(priority);
                    }else {
                        CopyerSpringUtil.copyProperties(priority, entity);
                        updatePriority(entity);
                    }
                }
            }
        }
    }


    public List<Priority> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<Priority> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getPriorityList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("priorityNameFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return new ArrayList<Priority>();
    }

    private List<Priority> searchList(String field,String keyword){
        List<Priority> list = getPriorityList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getPriorityList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
