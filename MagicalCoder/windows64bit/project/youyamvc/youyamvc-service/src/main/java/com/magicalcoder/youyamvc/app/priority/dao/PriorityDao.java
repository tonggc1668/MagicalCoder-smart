package com.magicalcoder.youyamvc.app.priority.dao;

import com.magicalcoder.youyamvc.app.model.Priority;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface PriorityDao{
    Priority getPriority(Map<String, Object> query);
    List<Priority> getPriorityList(Map<String, Object> query);
    Integer getPriorityListCount(Map<String, Object> query);

    Long  insertPriority(Priority entity);    void batchInsertPriority(List<Priority> list);
    void batchUpdatePriority(List<Priority> list);
    void updatePriority(Priority entity);
    void updatePriorityWithoutNull(Priority entity);
    void updatePriorityByWhereSql(Map<String,Object> entity);

    void truncatePriority();
    void deletePriority(Map<String, Object> query);
    void deletePriorityList(Map<String, Object> query);
    void deletePriorityByWhereSql(Map<String, Object> query);
    void batchDeletePriority(List<Long> list);
    void batchDeletePriorityList(List<Priority> entityList);
}
