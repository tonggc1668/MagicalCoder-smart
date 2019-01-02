package com.magicalcoder.youyamvc.app.dict.dao.impl;

import com.magicalcoder.youyamvc.app.dict.dao.DictProxyDao;
import com.magicalcoder.youyamvc.app.model.Dict;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("dictProxyDao")
public class DictProxyDaoImpl implements DictProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}
