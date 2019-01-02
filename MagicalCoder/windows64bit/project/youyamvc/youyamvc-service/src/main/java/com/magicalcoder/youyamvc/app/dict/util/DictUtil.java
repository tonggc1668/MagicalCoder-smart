package com.magicalcoder.youyamvc.app.dict.util;

import com.magicalcoder.youyamvc.app.dict.service.DictService;
import com.magicalcoder.youyamvc.app.model.Dict;
import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;
import com.magicalcoder.youyamvc.core.common.utils.MapUtil;
import com.magicalcoder.youyamvc.core.spring.SpringContainer;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
public class DictUtil {
    public static String getDictValue(String dictKey){
        String cacheKey = buildCacheKey(dictKey);
        try {
            String value = CacheUtil.get(cacheKey);
            if(value != null){
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DictService dictService = SpringContainer.getBean("dictService", DictService.class);
        Dict dict = dictService.selectOneDictWillThrowException(MapUtil.buildMap("dictKey", dictKey));
        if(dict!=null){
            CacheUtil.resetCache(cacheKey, 1800, dict.getDictValue());
            return dict.getDictValue();
        }
        return null;
    }
    public static String buildCacheKey(String dictKey){
        return "DictUtil.buildCacheKey"+dictKey;
    }
    public static String refreshCache(String dictKey){
        CacheUtil.delete(buildCacheKey(dictKey));
        return getDictValue(dictKey);
    }
}
