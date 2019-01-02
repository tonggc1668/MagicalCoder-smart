package com.magicalcoder.youyamvc.app.module.dao.impl;

import com.magicalcoder.youyamvc.app.module.dao.ModuleDao;
import com.magicalcoder.youyamvc.app.model.Module;
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
@Component("moduleDao")
public class ModuleDaoImpl   implements ModuleDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public Module getModule(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ModuleMapper.getModule",query);
    }
    @Override
    public List<Module> getModuleList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("ModuleMapper.getModuleList", query);
    }
    @Override
    public Integer getModuleListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ModuleMapper.getModuleListCount", query);
    }

    @Override
    public void batchInsertModule(List<Module> list) {
        sqlSessionTemplate.insert("ModuleMapper.batchInsertModule",list);
    }

    @Override
    public void batchUpdateModule(List<Module> list) {
        sqlSessionTemplate.update("ModuleMapper.batchUpdateModule",list);
    }

    @Override
    public     Long  insertModule(Module entity) {
        sqlSessionTemplate.insert("ModuleMapper.insertModule",entity);
  return entity.getId();    }
    @Override
    public void updateModule(Module entity) {
        sqlSessionTemplate.update("ModuleMapper.updateModule", entity);
    }
    @Override
    public void updateModuleWithoutNull(Module entity) {
        sqlSessionTemplate.update("ModuleMapper.updateModuleWithoutNull", entity);
    }
    @Override
    public void updateModuleByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("ModuleMapper.updateModuleByWhereSql", entity);
    }

    @Override
    public List<Module> getModuleOneToOneRelateList(Map<String, Object> query){
        return sqlSessionTemplate.selectList("ModuleMapper.getModuleOneToOneRelateList", query);
    }
    @Override
    public Integer getModuleOneToOneRelateListCount(Map<String, Object> query){
        return sqlSessionTemplate.selectOne("ModuleMapper.getModuleOneToOneRelateListCount", query);
    }
    @Override
    public void truncateModule() {
        sqlSessionTemplate.delete("ModuleMapper.truncateModule");
    }
    @Override
    public void deleteModule(Map<String, Object> query) {
        sqlSessionTemplate.delete("ModuleMapper.deleteModule",query);
    }
    @Override
    public void deleteModuleList(Map<String, Object> query) {
        sqlSessionTemplate.delete("ModuleMapper.deleteModuleList",query);
    }
    @Override
    public void deleteModuleByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("ModuleMapper.deleteModuleByWhereSql",query);
    }
    @Override
    public void batchDeleteModule(List<Long> list) {
        sqlSessionTemplate.delete("ModuleMapper.batchDeleteModule",list);
    }
    @Override
    public void batchDeleteModuleList(List<Module> list) {
        sqlSessionTemplate.delete("ModuleMapper.batchDeleteModuleList",list);
    }

}
