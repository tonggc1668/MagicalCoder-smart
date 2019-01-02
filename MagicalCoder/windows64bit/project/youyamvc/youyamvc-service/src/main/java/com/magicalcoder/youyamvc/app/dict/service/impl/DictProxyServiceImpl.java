package com.magicalcoder.youyamvc.app.dict.service.impl;

import com.magicalcoder.youyamvc.app.dict.dao.DictProxyDao;
import com.magicalcoder.youyamvc.app.dict.service.DictService;
import com.magicalcoder.youyamvc.app.dict.service.DictProxyService;
import com.magicalcoder.youyamvc.app.model.Dict;
import com.magicalcoder.youyamvc.app.model.dict.Dictionary;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
/**
    代理类 保证了service dao的自动生成 有改动业务 直接写在此类处
*/
/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("dictProxyService")
public class DictProxyServiceImpl implements DictProxyService{

    @Resource(name="dictProxyDao")
    private DictProxyDao dictProxyDao;

    @Resource(name="dictService")
    private DictService dictService;
    @Override
    public List<Dict> getAllDict() {
        return dictService.getDictList(null);
    }

    @Override
    public void refresh() {
        System.out.println("手动：数据库字典初始化...");
        Dictionary.flush();
        List<Dict> allDict = getAllDict();
        if(ListUtils.isNotBlank(allDict)){
            for(Dict dict : allDict){
                Dictionary.autoInit(dict);
            }
        }
        System.out.println("手动：数据库字典初始化完成^ ^");
    }
}
