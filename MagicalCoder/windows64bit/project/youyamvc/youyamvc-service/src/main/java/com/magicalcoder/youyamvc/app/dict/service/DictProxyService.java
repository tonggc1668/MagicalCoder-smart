package com.magicalcoder.youyamvc.app.dict.service;

import com.magicalcoder.youyamvc.app.model.Dict;

import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
public interface DictProxyService{
    List<Dict> getAllDict();
    void refresh();
}
