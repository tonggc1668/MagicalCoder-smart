package com.magicalcoder.youyamvc.app.dict.dao.impl;

import com.magicalcoder.youyamvc.app.dict.dao.DictDao;
import com.magicalcoder.youyamvc.app.model.Dict;
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
@Component("dictDao")
public class DictDaoImpl   implements DictDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public Dict getDict(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("DictMapper.getDict",query);
    }
    @Override
    public List<Dict> getDictList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("DictMapper.getDictList", query);
    }
    @Override
    public Integer getDictListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("DictMapper.getDictListCount", query);
    }

    @Override
    public void batchInsertDict(List<Dict> list) {
        sqlSessionTemplate.insert("DictMapper.batchInsertDict",list);
    }

    @Override
    public void batchUpdateDict(List<Dict> list) {
        sqlSessionTemplate.update("DictMapper.batchUpdateDict",list);
    }

    @Override
    public     Long  insertDict(Dict entity) {
        sqlSessionTemplate.insert("DictMapper.insertDict",entity);
  return entity.getId();    }
    @Override
    public void updateDict(Dict entity) {
        sqlSessionTemplate.update("DictMapper.updateDict", entity);
    }
    @Override
    public void updateDictWithoutNull(Dict entity) {
        sqlSessionTemplate.update("DictMapper.updateDictWithoutNull", entity);
    }
    @Override
    public void updateDictByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("DictMapper.updateDictByWhereSql", entity);
    }

    @Override
    public void truncateDict() {
        sqlSessionTemplate.delete("DictMapper.truncateDict");
    }
    @Override
    public void deleteDict(Map<String, Object> query) {
        sqlSessionTemplate.delete("DictMapper.deleteDict",query);
    }
    @Override
    public void deleteDictList(Map<String, Object> query) {
        sqlSessionTemplate.delete("DictMapper.deleteDictList",query);
    }
    @Override
    public void deleteDictByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("DictMapper.deleteDictByWhereSql",query);
    }
    @Override
    public void batchDeleteDict(List<Long> list) {
        sqlSessionTemplate.delete("DictMapper.batchDeleteDict",list);
    }
    @Override
    public void batchDeleteDictList(List<Dict> list) {
        sqlSessionTemplate.delete("DictMapper.batchDeleteDictList",list);
    }

}
