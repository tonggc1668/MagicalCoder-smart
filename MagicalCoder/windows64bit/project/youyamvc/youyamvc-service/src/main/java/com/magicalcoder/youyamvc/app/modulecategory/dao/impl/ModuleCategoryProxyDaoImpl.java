package com.magicalcoder.youyamvc.app.modulecategory.dao.impl;

import com.magicalcoder.youyamvc.app.modulecategory.dao.ModuleCategoryProxyDao;
import com.magicalcoder.youyamvc.app.model.ModuleCategory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("moduleCategoryProxyDao")
public class ModuleCategoryProxyDaoImpl implements ModuleCategoryProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}
