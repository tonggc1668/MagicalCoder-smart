package com.magicalcoder.youyamvc.app.dict.dao;

import com.magicalcoder.youyamvc.app.model.Dict;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface DictDao{
    Dict getDict(Map<String, Object> query);
    List<Dict> getDictList(Map<String, Object> query);
    Integer getDictListCount(Map<String, Object> query);

    Long  insertDict(Dict entity);    void batchInsertDict(List<Dict> list);
    void batchUpdateDict(List<Dict> list);
    void updateDict(Dict entity);
    void updateDictWithoutNull(Dict entity);
    void updateDictByWhereSql(Map<String,Object> entity);

    void truncateDict();
    void deleteDict(Map<String, Object> query);
    void deleteDictList(Map<String, Object> query);
    void deleteDictByWhereSql(Map<String, Object> query);
    void batchDeleteDict(List<Long> list);
    void batchDeleteDictList(List<Dict> entityList);
}
