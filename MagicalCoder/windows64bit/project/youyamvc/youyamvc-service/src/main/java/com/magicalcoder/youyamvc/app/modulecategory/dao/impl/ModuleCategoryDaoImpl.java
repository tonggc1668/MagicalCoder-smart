package com.magicalcoder.youyamvc.app.modulecategory.dao.impl;

import com.magicalcoder.youyamvc.app.modulecategory.dao.ModuleCategoryDao;
import com.magicalcoder.youyamvc.app.model.ModuleCategory;
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
@Component("moduleCategoryDao")
public class ModuleCategoryDaoImpl   implements ModuleCategoryDao {

    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
    @Override
    public ModuleCategory getModuleCategory(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ModuleCategoryMapper.getModuleCategory",query);
    }
    @Override
    public List<ModuleCategory> getModuleCategoryList(Map<String, Object> query) {
        return sqlSessionTemplate.selectList("ModuleCategoryMapper.getModuleCategoryList", query);
    }
    @Override
    public Integer getModuleCategoryListCount(Map<String, Object> query) {
        return sqlSessionTemplate.selectOne("ModuleCategoryMapper.getModuleCategoryListCount", query);
    }

    @Override
    public void batchInsertModuleCategory(List<ModuleCategory> list) {
        sqlSessionTemplate.insert("ModuleCategoryMapper.batchInsertModuleCategory",list);
    }

    @Override
    public void batchUpdateModuleCategory(List<ModuleCategory> list) {
        sqlSessionTemplate.update("ModuleCategoryMapper.batchUpdateModuleCategory",list);
    }

    @Override
    public     Long  insertModuleCategory(ModuleCategory entity) {
        sqlSessionTemplate.insert("ModuleCategoryMapper.insertModuleCategory",entity);
  return entity.getId();    }
    @Override
    public void updateModuleCategory(ModuleCategory entity) {
        sqlSessionTemplate.update("ModuleCategoryMapper.updateModuleCategory", entity);
    }
    @Override
    public void updateModuleCategoryWithoutNull(ModuleCategory entity) {
        sqlSessionTemplate.update("ModuleCategoryMapper.updateModuleCategoryWithoutNull", entity);
    }
    @Override
    public void updateModuleCategoryByWhereSql(Map<String,Object> entity) {
        sqlSessionTemplate.update("ModuleCategoryMapper.updateModuleCategoryByWhereSql", entity);
    }

    @Override
    public void truncateModuleCategory() {
        sqlSessionTemplate.delete("ModuleCategoryMapper.truncateModuleCategory");
    }
    @Override
    public void deleteModuleCategory(Map<String, Object> query) {
        sqlSessionTemplate.delete("ModuleCategoryMapper.deleteModuleCategory",query);
    }
    @Override
    public void deleteModuleCategoryList(Map<String, Object> query) {
        sqlSessionTemplate.delete("ModuleCategoryMapper.deleteModuleCategoryList",query);
    }
    @Override
    public void deleteModuleCategoryByWhereSql(Map<String, Object> query) {
        sqlSessionTemplate.delete("ModuleCategoryMapper.deleteModuleCategoryByWhereSql",query);
    }
    @Override
    public void batchDeleteModuleCategory(List<Long> list) {
        sqlSessionTemplate.delete("ModuleCategoryMapper.batchDeleteModuleCategory",list);
    }
    @Override
    public void batchDeleteModuleCategoryList(List<ModuleCategory> list) {
        sqlSessionTemplate.delete("ModuleCategoryMapper.batchDeleteModuleCategoryList",list);
    }

}
