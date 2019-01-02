package com.magicalcoder.youyamvc.app.priority.dao.impl;

import com.magicalcoder.youyamvc.app.priority.dao.PriorityDao;
import com.magicalcoder.youyamvc.app.model.Priority;
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
@Component("priorityDao")
public class PriorityDaoImpl   implements PriorityDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public Priority getPriority(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("PriorityMapper.getPriority",query);
    }
    @Override
    public List<Priority> getPriorityList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("PriorityMapper.getPriorityList", query);
    }
    @Override
    public Integer getPriorityListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("PriorityMapper.getPriorityListCount", query);
    }

    @Override
    public void batchInsertPriority(List<Priority> list) {
        sqlSessionTemplate.insert("PriorityMapper.batchInsertPriority",list);
    }

    @Override
    public void batchUpdatePriority(List<Priority> list) {
        sqlSessionTemplate.update("PriorityMapper.batchUpdatePriority",list);
    }

    @Override
    public     Long  insertPriority(Priority entity) {
        sqlSessionTemplate.insert("PriorityMapper.insertPriority",entity);
  return entity.getId();    }
    @Override
    public void updatePriority(Priority entity) {
        sqlSessionTemplate.update("PriorityMapper.updatePriority", entity);
    }
    @Override
    public void updatePriorityWithoutNull(Priority entity) {
        sqlSessionTemplate.update("PriorityMapper.updatePriorityWithoutNull", entity);
    }
    @Override
    public void updatePriorityByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("PriorityMapper.updatePriorityByWhereSql", entity);
    }

    @Override
    public void truncatePriority() {
        sqlSessionTemplate.delete("PriorityMapper.truncatePriority");
    }
    @Override
    public void deletePriority(Map<String, Object> query) {
        sqlSessionTemplate.delete("PriorityMapper.deletePriority",query);
    }
    @Override
    public void deletePriorityList(Map<String, Object> query) {
        sqlSessionTemplate.delete("PriorityMapper.deletePriorityList",query);
    }
    @Override
    public void deletePriorityByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("PriorityMapper.deletePriorityByWhereSql",query);
    }
    @Override
    public void batchDeletePriority(List<Long> list) {
        sqlSessionTemplate.delete("PriorityMapper.batchDeletePriority",list);
    }
    @Override
    public void batchDeletePriorityList(List<Priority> list) {
        sqlSessionTemplate.delete("PriorityMapper.batchDeletePriorityList",list);
    }

}
