
package com.magicalcoder.youyamvc.app.dict.service.impl;

import com.magicalcoder.youyamvc.app.dict.dao.DictDao;
import com.magicalcoder.youyamvc.app.dict.service.DictService;
import com.magicalcoder.youyamvc.app.model.Dict;
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
@Component("dictService")
public class DictServiceImpl implements DictService{
    @Resource(name="dictDao")
    private DictDao dictDao;


    @Override
    public Dict getDict(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return dictDao.getDict(query);
    }

    @Override
    public Dict selectOneDictWillThrowException(Map<String, Object> query){
        return dictDao.getDict(query);
    }

    @Override
    public List<Dict> getDictList(Map<String, Object> query) {
        return dictDao.getDictList(query);
    }

    @Override
    public Integer getDictListCount(Map<String, Object> query) {
        return dictDao.getDictListCount(query);
    }

    @Override
    public     Long  insertDict(Dict entity) {
        return dictDao.insertDict(entity);
    }

    @Override
    public void updateDict(Dict entity) {
        //校验
        dictDao.updateDict(entity);
    }

    @Override
    public void updateDictWithoutNull(Dict entity) {
        //校验
        dictDao.updateDictWithoutNull(entity);
    }

    @Override
    public void updateDictByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        dictDao.updateDictByWhereSql(entity);
    }

    @Override
    public void deleteDict(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        dictDao.deleteDict(query);
    }
    @Override
    public void deleteDictList(Map<String,Object> entity){
        dictDao.deleteDictList(entity);
    }

    @Override
    public void deleteDictByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        dictDao.deleteDictByWhereSql(query);
    }


    @Override
    public void truncateDict() {
        dictDao.truncateDict();
    }

    @Override
    public void batchInsertDict(List<Dict> list) {
        //校验
        dictDao.batchInsertDict(list);
    }

    @Override
    public void batchUpdateDict(List<Dict> list) {
        //校验
        dictDao.batchUpdateDict(list);
    }
    @Override
    public void batchDeleteDict(List<Long> idList) {
        dictDao.batchDeleteDict(idList);
    }

    @Override
    public void batchDeleteDictList(List<Dict> entityList){
        dictDao.batchDeleteDictList(entityList);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<Dict> list) {
        if(list!=null && list.size()>0){
            for(Dict dict : list){
                if (dict.getId() == null) {
                    insertDict(dict);
                } else {
                    Dict entity = getDict(dict.getId());
                    if(entity==null){
                        insertDict(dict);
                    }else {
                        CopyerSpringUtil.copyProperties(dict, entity);
                        updateDict(entity);
                    }
                }
            }
        }
    }


    public List<Dict> tryQueryList(String keyword,String selectValue, String foreignJavaField){
        List<Dict> list;
        if(StringUtils.isBlank(keyword) || "全部".equals(keyword)){
            return getDictList(ProjectUtil.buildMap("limitIndex",0,"limit", 20));
        }else{
            list = searchList("dictKeyFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            list = searchList("dictValueFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("dictTypeFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
            list = searchList("dictDescFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            if(ProjectUtil.isNum(keyword)){
            list = searchList("dictOrderFirst",keyword);
            if(ListUtils.isNotBlank(list)){
                return list;
            }
            }
        }
        return new ArrayList<Dict>();
    }

    private List<Dict> searchList(String field,String keyword){
        List<Dict> list = getDictList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = getDictList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }
}
